package com.ast.dsl.functions

/**
 * list of case classes used for retail promotion definition
 */
trait PromoFunctions {
  //expression trait
  sealed abstract trait Expr
  //rules 
  sealed abstract trait Rule extends Expr
  //promotion case class
  case class Promotion(rules: List[Rule], promo: Promo)
  //promo case class
  case class Promo(v1: Var) extends Expr
  //variable case class
  case class Var(varname: String) extends Expr
  //input list
  case class InputList[T](varList: List[T]) extends Expr
  //sku with list of sku names
  case class SKURule[T](list: InputList[T]) extends Rule
  //category with list of categories
  case class CategoryRule[T](list: InputList[T]) extends Rule
}
