package chrisbrn.iqs_api.services

import chrisbrn.iqs_api.config.AppConfig

import org.springframework.beans.factory.annotation.Qualifier



fun dosomething(){



    print(AppConfig.getSigner())

}