package com.trianglz.mimar.modules.cart.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class CartSocketServiceQualifier(
    /** The name.  */
    val value: String = ""
)

