package com.ast.dsl.functions

/**
 * Enumeration for Rulefor
 */
object RuleFor extends Enumeration {
  val CATEGORY, SKU = Value
}

/**
 * list of case classes used for retail promotion definition
 */
trait PromoFunctions {
  //expression trait
  sealed abstract trait Expr
  //promotion case class
  case class Promotion(rules: List[Rule[_]], promo: Promo)
  //promo case class
  case class Promo(v1: Var) extends Expr
  //variable case class
  case class Var(varname: String) extends Expr
  //input list
  case class InputList[T](varList: List[T]) extends Expr
  //Rule for various rule types 
  case class Rule[T](ruleType: RuleFor.Value, list: InputList[T]) extends Expr
}
