package hugebox

import com.github.scribejava.core.model.OAuth2AccessToken
import grails.converters.JSON

class LoginController {
    def toolsService
    def springSecurityService
    def googleOAuth2Service

    def index()
    {
        OAuth2AccessToken token = googleOAuth2Service.getAccessToken(params.code)
        def response = JSON.parse(googleOAuth2Service.getResponse(token).body)

        User user = User.findByUsername(response.email)
        if(!user)
        {
            user = new User(
                    username: response.email,
                    password: springSecurityService.encodePassword(toolsService.generatePassword()),
                    name: response.given_name ?: response.name,
                    profilePictureUrl: response.picture
            )
        }
        else
        {
            user.name = response.given_name ?: response.name
            user.profilePictureUrl = response.picture
        }
        user.save(flush: true)
        springSecurityService.reauthenticate(user.username)

        redirect url: "/"
    }
}