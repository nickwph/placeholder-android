package com.nicholasworkshop.placeholder.model.adapter

import com.nicholasworkshop.placeholder.model.User

fun parse(user: com.nicholasworkshop.placeholder.api.UserService.User): User {
    return User(
            id = user.id!!,
            name = user.name,
            username = user.username,
            email = user.email,
            phone = user.phone,
            website = user.website,
            companyName = user.company?.name,
            companyCatchPhrase = user.company?.catchPhrase,
            companyBs = user.company?.bs,
            addressStreet = user.address?.street,
            addressSuite = user.address?.suite,
            addressCity = user.address?.city,
            addressZipcode = user.address?.zipcode,
            addressGeoLat = user.address?.geo?.lat,
            addressGeoLng = user.address?.geo?.lng)
}

fun parse(userList: List<com.nicholasworkshop.placeholder.api.UserService.User>): List<User> {
    return userList.map(::parse)
}