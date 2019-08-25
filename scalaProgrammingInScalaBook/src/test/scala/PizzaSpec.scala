import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.GivenWhenThen


class PizzaSpec extends FunSpec with BeforeAndAfter with GivenWhenThen {

  var pizza: Pizza = _

  before {
    pizza = new Pizza
  }

  describe("A Pizza") {

    it("should start with no toppings") {
      assert(pizza.getToppings.size == 0)
    }

    it("should allow addition of toppings") (pending)

    it("should allow removal of toppings") (pending)
  }


  describe("GivenWhenThen: A Pizza") {

    it ("should allow the addition of toppings") {
      Given("a new pizza")
      pizza = new Pizza

      When("a topping is added")
      pizza.addTopping(Topping("green olives"))

      Then("the topping count should be incremented")
      assertResult(1) {
        pizza.getToppings.size
      }

      And("the topping should be what was added")
      val t = pizza.getToppings(0)
      assert(t === new Topping("green olives"))
    }
  }



}
