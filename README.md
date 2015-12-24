<h1>Retail Promotion DSL</h1>

This code demonstrate how to use Scala StandardTokenParsers with parser combinators. I would like to narrate my experience in designing an external DSL for a retail application using the parser combinator library of Scala.

Few sample DSLs (simplified for brevity) for accepting promotions defination from the business users to define promotion for SKUs or categories. 

<li>Promotion for SKu(1234,5678,8965,1278) discount 10 percentage</li>
<li>Promotion for Category(Toys, Dairy) discount 15 percentage</li> 
<li>Promotion for SKu(1234,5678,8965,1278),Category(Toys, Dairy) discount 10 percentage</li> 

As we see list of SKUs or categories and amount of discount varies in these promotion definitions. So it good idea to make few promotion templates and keep SKU List, Category list and amount of discount as variable, so that user should supply these variable values to define promotion. This will also allow us to define user interface effectively to define such promotions. 




