package com.intern.conjob.ui.home.matching

import com.intern.conjob.data.model.Post
import com.intern.conjob.ui.base.BaseViewModel

class PostDetailViewModel: BaseViewModel() {
    fun getTempData(): List<Post> {
        return listOf(
            Post(
                0,
                "",
                "Opacity hand hand fill move style bullet. Export flows line strikethrough flows #UIUX #trending #social #job #more",
                "NhaTuyenDungA",
                "https://cdn.eva.vn/upload/2-2020/images/2020-05-05/don-xin-viec-ba-dao-6-1588672247-471-width1212height798.jpg",
                "Img"
            ),
            Post(
                1,
                "",
                "Hello mọi người, đây là trang tuyển dụng của công ty mình. Mọi người thấy hứng thú thì apply vào công ty mình nhé! ^^",
                "NhaTuyenDungB",
                "https://jobsgo.vn/blog/wp-content/uploads/2023/03/stt-tuyen-dung-hay-content-tuyen-dung-hai-huoc.png",
                "Img"
            ),
            Post(
                3,
                "",
                "Hello mọi người, đây là trang tuyển dụng của công ty mình. Mọi người thấy hứng thú thì apply vào công ty mình nhé! ^^",
                "NhaTuyenDungC",
                "https://videos.pexels.com/video-files/7232007/7232007-uhd_2160_3840_25fps.mp4",
                "Video"
            )
        )
    }
}