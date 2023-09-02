package com.trianglz.mimar.modules.socket.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class SocketScopeQualifier(
    /** The name.  */
    val value: String = ""
)

