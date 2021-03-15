
require(x != null)  //throws IllegalArgumentException - to check fun or constructor args
check(x != null)   //throws IllegalStateException - to check the internal state (mutable props)
assert(x != null)  //disappears in production (compile in debug mode)

requireNotNull(x)