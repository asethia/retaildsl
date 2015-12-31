package com.ast.dsl.parser

import com.ast.dsl.functions.PromoFunctions

/**
 * this provides evaluation of promotions AST tree
 */
trait RetailPromotionEval extends PromoFunctions {

  def createPromoFromTemplate(promoTemplate: PromotionTemplate,context: Map[String, List[String]]):Promotion = {
    //replace 
    val rules = promoTemplate.rules map { rule => dynamicRule(rule, context) }
    Promotion(rules, dynamicPromo(promoTemplate.promo,context))
  }

  private def dynamicPromo[T](promoTemplate: Promo[T], context: Map[String, List[String]]):Promo[String] = {
    val discount = promoTemplate.value match {
      case Var(name: String) => context.get(name) match {
        case Some(list: List[String]) => list.head
        case _                        => "0"
      }
      case _:String => promoTemplate.value
    }
    Promo(discount.toString)
  }

  private def dynamicRule[T](ruleTemplate: Rule[T], context: Map[String, List[String]]): Rule[String] = {
    //a rule can have InputList and InputList is List of either String or Vars
    //this is going to replace all variables with actual value provided by the context
    val dynamicInputList = ruleTemplate.inputList.varList map { x =>
      x match {
        case Var(name: String) => context.get(name) match {
          case Some(list: List[String]) => list
          case _                        => List()
        }
        case _: String => List(x.toString)
      }
    }
    //create new rule without variables 
    Rule(ruleTemplate.ruleType, InputList(dynamicInputList.flatten))
  }
}