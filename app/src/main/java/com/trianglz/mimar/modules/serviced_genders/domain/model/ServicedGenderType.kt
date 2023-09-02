package com.trianglz.mimar.modules.serviced_genders.domain.model


sealed class ServicedGenderType(val serverName: String){
    object Male: ServicedGenderType("male")
    object Female: ServicedGenderType("female")
    object Both: ServicedGenderType("both")
}

fun getServicedGender(gender: String): ServicedGenderType {
    return when(gender){
         ServicedGenderType.Male.serverName ->{
             ServicedGenderType.Male
         }

        ServicedGenderType.Female.serverName ->{
            ServicedGenderType.Female
        }

        else ->  ServicedGenderType.Both
    }
}

fun List<ServicedGenderDomainModel>.getServicedGenderFilterKey()= when(this.size){
    2 -> ServicedGenderType.Both.serverName
    1 -> this[0].value
    else -> null
}