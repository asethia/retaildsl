package com.ast.dsl.parser

import scala.util.parsing.combinator.syntactical._
import com.ast.dsl.functions.PromoFunctions
import com.ast.dsl.functions.RuleFor

/**
 * this is parser for retail promotion
 * It can parse promotion definition as
 * Promotion for SKu (1223,12345,6789) discount 10 %
 * or It can be "Promotion for SKu(Var(listOfSkus1),Var(listOfSkus2)) discount 10 percentage"
 * or It can be "Promotion for SKu(Var(listOfSkus1),Var(listOfSkus2)),Category(Baby,Toys) discount 10 %"
 */
 
trait RetailPromotionParser extends StandardTokenParsers with PromoFunctions {

  //lexical delimiters
  lexical.delimiters ++= List("(", ")", ",")
  //lexical reserved keywords , this can extend based on use case
  lexical.reserved += ("Promotion", "for", "Sku", "Category", "discount", "percentage", "Var")

  //root of grammar
  def promo_expr: Parser[Promotion] = promo_spec ~> promo_rule ~ discount_action ^^ { case rules ~ award => Promotion(rules, award) }

  def promo_spec = "Promotion" ~ "for"

  //repeat sku and category rule 
  def promo_rule = rep1sep((sku_rule | category_rule), ",") ^^ { case rules => rules }

  def sku_rule = "Sku" ~ "(" ~> (variable_rule | numer_string_list) <~ ")" ^^ { list => Rule(RuleFor.SKU,list) }

  def category_rule = "Category" ~ "(" ~> (variable_rule | numer_string_list) <~ ")" ^^ { list => Rule(RuleFor.CATEGORY,list) }

  //variable lexical rule
  def variable_rule = rep1sep(variable, ",") ^^ { case varlist => InputList(varlist) }

  //either numeric or string literal lexical rule
  def numer_string_list = rep1sep((stringLit | numericLit), ",") ^^ { case listdata => InputList(listdata) }

  //parse discount and use combinator to get variable
  def discount_action = "discount" ~> (variable) <~ "percentage" ^^ {case n=>Promo(n)}

  //variable parsing
  def variable = ("Var" ^^^ Var) ~ ("(" ~> ident <~ ")") ^^ { case v ~ n => v(n) }

  //lexical parsing
  def parse(s: String) = {
    val tokens = new lexical.Scanner(s)
    phrase(promo_expr)(tokens)
  }
}
