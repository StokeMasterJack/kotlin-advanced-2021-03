package kws.b_contracts

class Card(val value: Int?, val suit: Int?) {

    init {
//        assert(value != null)   //disappears in production (compile in debug mode)
//        require(value != null)  //throws IllegalArgumentException - to check fun or constructor args
//        check(value != null)    //throws IllegalStateException - to check the internal state (mutable props)

        requireNotNull(value)
        requireNotNull(suit)

    }


}


