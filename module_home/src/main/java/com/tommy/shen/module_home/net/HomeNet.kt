package com.tommy.shen.module_home.net

import com.tommy.shen.module_common.http.NetWork

object Instance{

    val api by lazy { NetWork.createApi<HomeService>() }

}
