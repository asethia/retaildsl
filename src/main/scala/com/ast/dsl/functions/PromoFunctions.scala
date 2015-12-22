package com.ast.dsl.functions

/**
 * list of case classes used for retail promotion definition
 */
trait PromoFunctions {
  //expression trait
  sealed abstract trait Expr
  //promotion case class
  case class Promotion(rules:List[Rules],promo:Promo)
  //promo case class
  case class Promo(v1:Var) extends Expr
  //variable case class 
  case class Var(varname:String) extends Expr
  sealed abstract trait Rule extends Expr
  //sku with list of variables
  case class SKUVarRule(vl1:List[Var]) extends Rule
  //category with list of categories
  case class CategoryVarRule(vl1:List[Var]) extends Rule
  //sku with list of sku names
  case class SKURule(skuList:List[String]) extends Rule
  //category with list of categories
  case class CategoryRule(catList:List[String]) extends Rule
}
