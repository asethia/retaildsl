package com.ast.dsl

import scala.reflect.runtime.universe._
import com.ast.dsl.parser._


/**
 * this is main class to test Retail DSL Promotion
 */
object RetailDSLPromotionCLI extends RetailPromotionParser with RetailPromotionEval {

  def main(args: Array[String]) {
    val context: Map[String, List[String]] = Map(("offer_skulist1", List("123", "456")), ("offer_skulist2", List("789", "123")), ("offer_catlist1", List("Food", "Toys")),("x_percetage",List("10")))
    parse("Promotion for Sku(Var(offer_skulist1),Var(offer_skulist2)),Category(Var(offer_catlist1)) discount Var(x_percetage) percentage") match {
      case Success(result: PromotionTemplate, _) => {println("template "+ result)
        println(createPromoFromTemplate(result,context))
      }
      case Failure(msg, _) => println(msg)
      case Error(msg, _)   => println(msg)
    }
    //having both numeric and string sku numbers and category with variable
    parse("Promotion for Sku(33333,4444,'ssss'),Category(Var(offer_catlist1)) discount Var(x_percetage) percentage") match {
      case Success(result: PromotionTemplate, _) => {
        println("template "+ result)
        println(createPromoFromTemplate(result,context))
      }
      case Failure(msg, _) => println(msg)
      case Error(msg, _)   => println(msg)
    }
  }
  
 
}
