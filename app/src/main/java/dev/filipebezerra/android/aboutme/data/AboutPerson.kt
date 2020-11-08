package dev.filipebezerra.android.aboutme.data

data class AboutPerson(
    var fullName: String,
    var roles: String,
    var bio: String
) {
    override fun toString() = fullName
}