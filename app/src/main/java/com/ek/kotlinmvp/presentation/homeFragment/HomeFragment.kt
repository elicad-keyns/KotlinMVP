package com.ek.kotlinmvp.presentation.homeFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.data.db.HeroDatabase
import com.ek.kotlinmvp.data.db.dao.HeroDao
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.presentation.heroFragment.HeroDBAdapter
import com.ek.kotlinmvp.presentation.heroFragment.HeroFragmentDirections
import kotlinx.android.synthetic.main.fragment_hero.*
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class HomeFragment : MvpAppCompatFragment(R.layout.fragment_home), IHomeView {

    // Презентеры
    @InjectPresenter
    lateinit var homePresenter: HomePresenter

    // Вьюшка
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val context: Context = requireActivity().applicationContext
        homePresenter.context = context

        return root
    }

//    override fun openLoading() {
//        tv_home_connect.visibility = GONE
//        pb_home.visibility = VISIBLE
//    }
//
//    override fun hideLoading() {
//        pb_home.visibility = GONE
//    }
//
//    override fun openConnectFail() {
//        pb_home.visibility = GONE
//        tv_home_connect.visibility = VISIBLE
//    }
//
//    override fun hideConnectFail() {
//        tv_home_connect.visibility = GONE
//    }

    override fun getDataFromDB(page: Int) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase(context = requireContext())
        val heroDao: HeroDao? = db?.heroDao()

        val heroes: List<Hero> = heroDao!!.getHeroesByPage(hero_page = page)

//        if (heroes.isNullOrEmpty()) {
//            openConnectFail()
//            hideLoading()
//            rv_heroes.visibility = GONE
//            return
//        } else {
//            rv_heroes.visibility = VISIBLE
//            // Скрываем прогресс бар и ошибку соединения
//            hideLoading()
//            hideConnectFail()
//        }

        // Получаем макс страницы с бд
        if (homePresenter.maxPages == null)
            if (heroes[0].hero_max_pages != null)
                homePresenter.maxPages = heroes[0].hero_max_pages

        val heroDBAdapter: HeroDBAdapter = HeroDBAdapter(
            heroes,
            requireContext(),
            object : HeroDBAdapter.Callback {
                override fun onItemClicked(item: Hero) {
                    val action = HeroFragmentDirections.actionNavigationHeroToHeroInfo(
                        item.hero_id,
                        item.hero_name,
                        item.hero_status,
                        item.hero_species,
                        item.hero_type,
                        item.hero_gender,
                        item.hero_origin_name,
                        item.hero_location_name,
                        item.hero_created,
                        item.hero_image
                    )
                    Navigation.findNavController(root).navigate(action)
                }
            })

        // Проверяем ссылку на объект (Если попытаться быстро переключить фрагменты, то может вылететь ошибка без этого)
        rv_home_heroes?.let {
            it.adapter = heroDBAdapter
        }
    }
}