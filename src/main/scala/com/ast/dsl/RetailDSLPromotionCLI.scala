package com.ast.dsl

import com.ast.dsl.parser.RetailPromotionParser

/**
 * this is main class to test Retail DSL Promotion
 */
object RetailDSLPromotionCLI extends RetailPromotionParser {
  
  def main(args:Array[String]){
    parse("Promotion for Sku(Var(offer_skulist1),Var(offer_skulist1)),Category(Var(offer_catlist)) discount Var(x_percetage) percentage") match {
      case Success(result,_)=> {
          println(result)
      }
      case Failure(msg,_)=> println(msg)
      case Error(msg,_)=> println(msg)
    }
    //having both numeric and string sku numbers and category with variable
    parse("Promotion for Sku(33333,4444,'ssss'),Category(Var(offer_catlist)) discount Var(x_percetage) percentage") match {
      case Success(result:Promotion,_)=> { println(result)
      }
      case Failure(msg,_)=> println(msg)
      case Error(msg,_)=> println(msg)
    }
    
  }

}
