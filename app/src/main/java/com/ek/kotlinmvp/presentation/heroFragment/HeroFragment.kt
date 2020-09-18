package com.ek.kotlinmvp.presentation.heroFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.data.db.HeroDatabase
import com.ek.kotlinmvp.data.db.dao.HeroDao
import com.ek.kotlinmvp.data.db.entity.Hero
import kotlinx.android.synthetic.main.fragment_hero.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HeroFragment : MvpAppCompatFragment(R.layout.fragment_hero), IHeroView {

    // Презентеры
    @InjectPresenter
    lateinit var heroPresenter: HeroPresenter

    // Вьюшка
    private lateinit var root: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPageText()

        // Обработчик для переключений страниц вперед
        b_next_page.setOnClickListener {
            openLoading()
            heroPresenter.pageNext()
            setPageText()
        }

        // Обработчик для переключения страниц назад
        b_prev_page.setOnClickListener {
            openLoading()
            heroPresenter.pagePrev()
            setPageText()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_hero, container, false)

        val context: Context = requireActivity().applicationContext
        heroPresenter.context = context

        return root
    }

    // Показываем текущую страницу
    override fun setPageText() {
        tv_page.text = heroPresenter.page.toString()
    }

    //region open/hide ui
    // Открыть загрузку и скрить recycler
    override fun openLoading() {
        rv_heroes.visibility = GONE
        pb_hero.visibility = VISIBLE
    }

    // Скрыть загрузку и открыть recycler
    override fun hideLoading() {
        pb_hero.visibility = GONE
        rv_heroes.visibility = VISIBLE
    }

    override fun openConnectFail() {
        tv_connect.visibility = VISIBLE
        rv_heroes.visibility = GONE
    }

    override fun hideConnectFail() {
        tv_connect.visibility = GONE
    }
    //endregion

    override fun getDataFromDB(page: Int) {
        val db: HeroDatabase? = HeroDatabase.getHeroDatabase()
        val heroDao: HeroDao? = db?.heroDao()

        val heroes: List<Hero> = heroDao!!.getHeroesByPage(hero_page = page)

        if (heroes.isNullOrEmpty()) {
            openConnectFail()
            hideLoading()
            rv_heroes.visibility = GONE
            return
        } else {
            rv_heroes.visibility = VISIBLE
            // Скрываем прогресс бар и ошибку соединения
            hideLoading()
            hideConnectFail()
        }

        // Получаем макс страницы с бд
        if (heroPresenter.maxPages == null)
            if (heroes[0].hero_max_pages != null)
                heroPresenter.maxPages = heroes[0].hero_max_pages

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
        rv_heroes?.let {
            it.adapter = heroDBAdapter
        }
    }

//    override fun onResume() {
//        super.onResume()
//        //heroPresenter.networkRegister()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        //heroPresenter.networkUnregister()
//    }

//    // Если получили ответ апи
//    override fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty) {
//        // Проверяем, есть ли переменная с максимальным кол-вом страниц, если нету, то создаем
//        if (heroPresenter.maxPages == null) heroPresenter.maxPages = rickAndMorty.info.pages
//
//        // При нажатии на героя, открываем окно с полной информацией о нем
//        val heroAdapter = HeroAdapter(
//            rickAndMorty.results,
//            requireContext(),
//            object : HeroAdapter.Callback {
//                override fun onItemClicked(item: Results) {
//                    val action = HeroFragmentDirections.actionNavigationHeroToHeroInfo(
//                        item.id,
//                        item.name,
//                        item.status,
//                        item.species,
//                        item.type,
//                        item.gender,
//                        item.origin.name,
//                        item.location.name,
//                        item.created,
//                        item.image
//                    )
//                    Navigation.findNavController(root).navigate(action)
//                }
//            })
//
//        // Скрываем прогресс бар
//        hideLoading()
//
//        // Проверяем ссылку на объект (Если попытаться быстро переключить фрагменты, то может вылететь ошибка без этого)
//        rv_heroes?.let {
//            it.adapter = heroAdapter
//        }
//    }
//
//    // Если не получили ответ апи
//    override fun onDataErrorFromAPI(throwable: Throwable) {
//        Toast.makeText(activity, "Ошибка -> " + throwable.localizedMessage, LENGTH_SHORT).show()
//        hideLoading()
//        openConnectFail()
//    }
}