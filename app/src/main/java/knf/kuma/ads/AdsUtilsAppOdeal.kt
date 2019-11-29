package knf.kuma.ads

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.appodeal.ads.Appodeal
import com.appodeal.ads.InterstitialCallbacks
import com.appodeal.ads.RewardedVideoCallbacks
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import knf.kuma.R
import knf.kuma.commons.*
import knf.kuma.custom.BannerContainerView
import knf.kuma.news.AdNewsObject
import knf.kuma.news.NewsObject
import knf.kuma.pojos.Achievement
import knf.kuma.pojos.AchievementAd
import knf.kuma.pojos.FavoriteObject
import knf.kuma.pojos.RecentObject

object AdsUtilsAppOdeal {
    const val RECENT_BANNER = "recent_banner"
    const val RECENT_BANNER2 = "recent_banner_2"
    const val FAVORITE_BANNER = "favorite_banner"
    const val FAVORITE_BANNER2 = "favorite_banner_2"
    const val DIRECTORY_BANNER = "directory_banner"
    const val HOME_BANNER = "home_banner"
    const val HOME_BANNER2 = "home_banner_2"
    const val EMISSION_BANNER = "emission_banner"
    const val SEEING_BANNER = "seeing_banner"
    const val RECOMMEND_BANNER = "recommend_banner"
    const val QUEUE_BANNER = "queue_banner"
    const val RECORD_BANNER = "record_banner"
    const val RANDOM_BANNER = "random_banner"
    const val NEWS_BANNER = "news_banner"
    const val INFO_BANNER = "info_banner"
    const val ACHIEVEMENT_BANNER = "achievement_banner"
    const val EXPLORER_BANNER = "explorer_banner"
    const val CAST_BANNER = "cast_banner"
    const val REWARDED = "rewarded"
    const val INTERSTITIAL = "interstitial"
}

fun MutableList<RecentObject>.implAdsRecentAppOdeal() {
    if (!PrefsUtil.isAdsEnabled || isEmpty()) return
    var adIndex = 0
    ArrayList(this).forEachIndexed { index, _ ->
        if (index % 5 == 0 && index > 0) {
            val adID: String = when (adIndex) {
                0 -> {
                    adIndex = 1
                    AdsUtilsAppOdeal.RECENT_BANNER
                }
                else -> {
                    adIndex = 0
                    AdsUtilsAppOdeal.RECENT_BANNER2
                }
            }
            add(index, AdRecentObject(adID))
        }
    }
}

fun MutableList<FavoriteObject>.implAdsFavoriteAppOdeal() {
    if (!PrefsUtil.isAdsEnabled || isEmpty()) return
    var adIndex = 0
    ArrayList(this).apply {
        forEachIndexed { index, _ ->
            if (index % 8 == 0 && index > 0 && !this[index - 1].isSection) {
                val adID: String = when (adIndex) {
                    0 -> {
                        adIndex = 1
                        AdsUtilsAppOdeal.FAVORITE_BANNER
                    }
                    else -> {
                        adIndex = 0
                        AdsUtilsAppOdeal.FAVORITE_BANNER2
                    }
                }
                this@implAdsFavoriteAppOdeal.add(index, AdFavoriteObject(adID))
            }
        }
    }
}

fun MutableList<NewsObject>.implAdsNewsAppOdeal() {
    if (!PrefsUtil.isAdsEnabled || isEmpty()) return
    var adIndex = 0
    ArrayList(this).forEachIndexed { index, _ ->
        if (index % 5 == 0 && index > 0) {
            val adID: String = when (adIndex) {
                0 -> {
                    adIndex = 1
                    AdsUtilsAppOdeal.NEWS_BANNER
                }
                else -> {
                    adIndex = 0
                    AdsUtilsAppOdeal.NEWS_BANNER
                }
            }
            add(index, AdNewsObject(adID))
        }
    }
}

fun MutableList<Achievement>.implAdsAchievementAppOdeal() {
    if (!PrefsUtil.isAdsEnabled || isEmpty()) return
    var adIndex = 0
    ArrayList(this).forEachIndexed { index, _ ->
        if (index % 8 == 0 && index > 0) {
            val adID: String = when (adIndex) {
                0 -> {
                    adIndex = 1
                    AdsUtilsAppOdeal.ACHIEVEMENT_BANNER
                }
                else -> {
                    adIndex = 0
                    AdsUtilsAppOdeal.ACHIEVEMENT_BANNER
                }
            }
            add(index, AchievementAd(adID))
        }
    }
}

fun ViewGroup.implBannerCastAppOdeal() {
    this.implBannerAppOdeal(AdsUtilsAppOdeal.CAST_BANNER)
}

fun ViewGroup.implBannerAppOdeal(unitID: AdsType, isSmart: Boolean = false) {
    val id = when (unitID) {
        AdsType.RECENT_BANNER -> AdsUtilsAppOdeal.RECENT_BANNER
        AdsType.RECENT_BANNER2 -> AdsUtilsAppOdeal.RECENT_BANNER2
        AdsType.FAVORITE_BANNER -> AdsUtilsAppOdeal.FAVORITE_BANNER
        AdsType.FAVORITE_BANNER2 -> AdsUtilsAppOdeal.FAVORITE_BANNER2
        AdsType.DIRECTORY_BANNER -> AdsUtilsAppOdeal.DIRECTORY_BANNER
        AdsType.HOME_BANNER -> AdsUtilsAppOdeal.HOME_BANNER
        AdsType.HOME_BANNER2 -> AdsUtilsAppOdeal.HOME_BANNER2
        AdsType.EMISSION_BANNER -> AdsUtilsAppOdeal.EMISSION_BANNER
        AdsType.SEEING_BANNER -> AdsUtilsAppOdeal.SEEING_BANNER
        AdsType.RECOMMEND_BANNER -> AdsUtilsAppOdeal.RECOMMEND_BANNER
        AdsType.QUEUE_BANNER -> AdsUtilsAppOdeal.QUEUE_BANNER
        AdsType.RECORD_BANNER -> AdsUtilsAppOdeal.RECORD_BANNER
        AdsType.RANDOM_BANNER -> AdsUtilsAppOdeal.RANDOM_BANNER
        AdsType.NEWS_BANNER -> AdsUtilsAppOdeal.NEWS_BANNER
        AdsType.INFO_BANNER -> AdsUtilsAppOdeal.INFO_BANNER
        AdsType.ACHIEVEMENT_BANNER -> AdsUtilsAppOdeal.ACHIEVEMENT_BANNER
        AdsType.EXPLORER_BANNER -> AdsUtilsAppOdeal.EXPLORER_BANNER
        AdsType.CAST_BANNER -> AdsUtilsAppOdeal.CAST_BANNER
        AdsType.REWARDED -> AdsUtilsAppOdeal.REWARDED
        AdsType.INTERSTITIAL -> AdsUtilsAppOdeal.INTERSTITIAL
    }
    implBannerAppOdeal(id, isSmart)
}

fun ViewGroup.implBannerAppOdeal(unitID: String, isSmart: Boolean = false) {
    if (PrefsUtil.isAdsEnabled)
        doOnUI {
            val adView = inflate(context, R.layout.appodeal_ad)
            if (this is BannerContainerView) {
                show(adView)
            } else {
                removeAllViews()
                addView(adView)
            }
            Appodeal.setBannerViewId(R.id.appodealAd)
            context.findActivity()?.let { Appodeal.show(it, Appodeal.BANNER_VIEW) }
        }
}

fun getFAdLoaderRewardedAppOdeal(context: Activity, onUpdate: () -> Unit): FullscreenAdLoader = FAdLoaderRewardedAppOdeal(context, onUpdate)
fun getFAdLoaderInterstitialAppOdeal(context: Context, onUpdate: () -> Unit): FullscreenAdLoader = FAdLoaderInterstitialAppOdeal(context, onUpdate)

class FAdLoaderRewardedAppOdeal(private val context: Context, val onUpdate: () -> Unit) : FullscreenAdLoader {
    var isAdClicked = false

    init {
        Appodeal.setRewardedVideoCallbacks(object : RewardedVideoCallbacks {
            override fun onRewardedVideoFinished(p0: Double, p1: String?) {
                Answers.getInstance().logCustom(CustomEvent("Rewarded Ad watched"))
                Economy.reward(isAdClicked)
                onUpdate()
            }

            override fun onRewardedVideoClosed(p0: Boolean) {
            }

            override fun onRewardedVideoExpired() {
            }

            override fun onRewardedVideoLoaded(p0: Boolean) {
            }

            override fun onRewardedVideoClicked() {
                Answers.getInstance().logCustom(CustomEvent("Rewarded Ad clicked"))
                isAdClicked = true
            }

            override fun onRewardedVideoFailedToLoad() {
            }

            override fun onRewardedVideoShown() {
            }

            override fun onRewardedVideoShowFailed() {
            }
        })
    }

    override fun load() {
        isAdClicked = false
    }

    override fun show() {
        context.findActivity()?.let { Appodeal.show(it, Appodeal.REWARDED_VIDEO) }
    }
}

class FAdLoaderInterstitialAppOdeal(private val context: Context, val onUpdate: () -> Unit) : FullscreenAdLoader {
    var isAdClicked = false

    init {
        Appodeal.setInterstitialCallbacks(object : InterstitialCallbacks {
            override fun onInterstitialClicked() {
                Answers.getInstance().logCustom(CustomEvent("Interstitial Ad clicked"))
                isAdClicked = true
            }

            override fun onInterstitialLoaded(p0: Boolean) {
            }

            override fun onInterstitialShown() {
            }

            override fun onInterstitialShowFailed() {
            }

            override fun onInterstitialFailedToLoad() {
            }

            override fun onInterstitialClosed() {
                Answers.getInstance().logCustom(CustomEvent("Interstitial Ad watched"))
                Economy.reward(isAdClicked)
                onUpdate()
            }

            override fun onInterstitialExpired() {
            }
        })
    }

    override fun load() {
        isAdClicked = false
    }

    override fun show() {
        context.findActivity()?.let { Appodeal.show(it, Appodeal.INTERSTITIAL) }
    }
}