package com.ast.dsl.functions


/**
 * list of case classes used for retail promotion definition
 */
trait PromoFunctions {
  //expression trait
  sealed abstract trait Expr
  //rules 
  sealed abstract trait Rule extends Expr
  //rules with variables
  sealed abstract trait VarRule extends Rule
  //promotion case class
  case class Promotion(rules:List[Rule],promo:Promo)
  //promo case class
  case class Promo(v1:Var) extends Expr
  //variable case class 
  case class Var(varname:String) extends Expr 
  //variable list
  case class VarList(varList:List[Var]) extends Expr
  //string list
  case class StringList(stringList:List[String]) extends Expr
  //sku with list of variables
  case class SKUVarRule(list:VarList) extends VarRule
  //category with list of categories
  case class CategoryVarRule(list:VarList) extends VarRule
  //sku with list of sku names
  case class SKURule(list:StringList) extends Rule
  //category with list of categories
  case class CategoryRule(list:StringList) extends Rule
}
