<h1>Retail Promotion DSL</h1>

This code demonstrate how to use Scala StandardTokenParsers parser and combinators. I would like to narrate my experience in designing an external DSL for a retail application using the parser combinator library of Scala.

Few sample DSLs (simplified for brevity) for accepting promotions defination from the business users to define promotion for SKUs or categories. 

<li>Promotion for SKu(1234,5678,8965,1278) discount 10 percentage</li>
<li>Promotion for Category(Toys, Dairy) discount 15 percentage</li> 
<li>Promotion for SKu(1234,5678,8965,1278),Category(Toys, Dairy) discount 10 percentage</li> 

As we see list of SKUs or categories and amount of discount varies in these promotion definitions. So it good idea to make few promotion templates and keep SKU List, Category list and amount of discount as variable, so that user should supply these variable values to define multiple promotions. This will have following benefits:

1. This will allow to parse AST tree at once and eval at runt time based on defined various promotions. 
2. This will also allow us to define user interface effectively to define such promotions. 

The grammar of lexical parser can be extend further to accommodate various other keywords and functions. The current retail promotion grammar supports following keywords: <b><I>"Promotion", "for", "Sku", "Category", "discount", "percentage", "Var" </I></b>

The current project has following scala trait or classes:

1. PromoFunctions - List of Promotion functions defined based on the promotion object modeling.
2. RetailPromotionParser - Retail promotion parser to build AST Tree
3. RetailPromotionEval - Evaluate AST tree at run time based on run time context.

Few examples:

1. The AST tree for <b><I>“Promotion for Sku(Var(offer_skulist1),Var(offer_skulist2)),Category(Var(offer_catlist)) discount Var(x_percetage) percentage”</I></b> will be <b><I>“PromotionTemplate(List(Rule(SKU,InputList(List(Var(offer_skulist1), Var(offer_skulist2)))), Rule(CATEGORY,InputList(List(Var(offer_catlist1))))),Promo(Var(x_percetage)))”</I></b>
2. The AST tree for <b><I>“Promotion for Sku(33333,4444,'ssss'),Category(Var(offer_catlist)) discount Var(x_percetage) percentage”</I></b> will be <b><I>“PromotionTemplate(List(Rule(SKU,InputList(List(33333, 4444, ssss))), Rule(CATEGORY,InputList(List(Var(offer_catlist1))))),Promo(Var(x_percetage)))”</I></b>

Any node which is type of Var, means this node require run time value. PromotionTemplate indicates It is promotion template.

The RetailPromotionEval will able to create a new Promotion based on run time dynamic value. For example for above templates if we have following run time values:

1. ("offer_skulist1", List("123", "456"))
2. ("offer_skulist2", List("789", "123"))
3. ("offer_catlist1", List("abc", "xyz"))
4. ("x_percetage",List("10"))

<table>
<thead>
 <tr>
   <th align="left">Template</th>
   <th align="left">Promotion</th>
 </tr>
</thead>
<tbody>
 <tr>
  <td>PromotionTemplate(List(Rule(SKU,InputList(List(Var(offer_skulist1), Var(offer_skulist2)))), Rule(CATEGORY,InputList(List(Var(offer_catlist1))))),Promo(Var(x_percetage)))</td>
  <td>Promotion(List(Rule(SKU,InputList(List(123, 456, 789, 123))), Rule(CATEGORY,InputList(List(abc, xyz)))),Promo(10))</td>
 </tr>
 <tr>
  <td>PromotionTemplate(List(Rule(SKU,InputList(List(33333, 4444, ssss))), Rule(CATEGORY,InputList(List(Var(offer_catlist1))))),Promo(Var(x_percetage)))</td>
  <td>Promotion(List(Rule(SKU,InputList(List(33333, 4444, ssss))), Rule(CATEGORY,InputList(List(abc, xyz)))),Promo(10))</td>
</tr>
</tbody>
</table>

This is one of the sample for Retail DSL, this can extend for retail orders, retail loyalty offers, etc.
