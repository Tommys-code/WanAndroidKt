package com.tommy.shen.module_common.constant

import com.alibaba.android.arouter.launcher.ARouter

const val NEED_LOGIN = 2001

object Main {

    private const val MAIN = "/module_main"
    const val MAIN_PAGE = "$MAIN/main"
}

object Home {

    private const val HOME = "/module_home"
    const val HOME_PAGE = "$HOME/home"

    const val SEARCH_PAGE = "$HOME/search"
    const val SEARCH_RESULT_PAGE = "$HOME/search_result"

}

object Project {

    private const val PROJECT = "/module_project"
    const val PROJECT_PAGE = "$PROJECT/project"
    const val PROJECT_LIST_PAGE = "$PROJECT/project_list"
}

object Public {

    private const val PUBLIC = "/module_public"
    const val PUBLIC_PAGE = "$PUBLIC/public"

}

object Mine {
    private const val MINE = "/module_mine"
    const val MINE_PAGE = "$MINE/mine"

    const val MY_SCORE = "$MINE/my_score"
}

object Login {

    private const val LOGIN = "/module_login"
    const val LOGIN_PAGE = "$LOGIN/login"

    fun openLogin() {
        ARouter.getInstance().build(LOGIN_PAGE).navigation()
    }

    fun openRegister() {
        ARouter.getInstance().build(LOGIN_PAGE).withBoolean("register", true).navigation()
    }

}

object Web {

    private const val WEB = "/module_web"
    const val WEB_PAGE = "$WEB/web"

}

