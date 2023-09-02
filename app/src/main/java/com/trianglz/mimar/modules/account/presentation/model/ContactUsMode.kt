package com.trianglz.mimar.modules.account.presentation.model



sealed class ContactUsMode(val data: String)  {
    object SupportEmail : ContactUsMode("support@mimar.co")
    object SupportPhone : ContactUsMode("+2010")

}
