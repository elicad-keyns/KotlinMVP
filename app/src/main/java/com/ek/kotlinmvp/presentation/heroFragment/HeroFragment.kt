package com.ek.kotlinmvp.presentation.heroFragment

import Results
import RickAndMorty
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ek.kotlinmvp.R
import kotlinx.android.synthetic.main.fragment_hero.*

class HeroFragment : Fragment(), IHeroView {

    // Максимальное кол-во страниц
    var maxPages: Int? = null

    // текущая страница
    var page: Int = 1

    // Презентеры
    private lateinit var heroPresenter: HeroPresenter

    // Вьюшка
    private lateinit var root: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработчик для переключений страниц вперед
        b_next_page.setOnClickListener {
            if ((maxPages != null) && (page < maxPages!!)) {
                page++
                tv_page.text = page.toString()
                heroPresenter.getDataFromAPI(page = page)
            }
        }

        // Обработчик для переключения страниц назад
        b_prev_page.setOnClickListener {
            if (page > 1) {
                page--
                tv_page.text = page.toString()
                heroPresenter.getDataFromAPI(page = page)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_hero, container, false)

        // Инициализация
        heroPresenter = HeroPresenter(this)

        // Получаем данные с апи при создании вьюшки
        heroPresenter.getDataFromAPI(page = page)

        return root
    }

    // Если получили ответ апи
    override fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty) {
        if (maxPages == null) maxPages = rickAndMorty.info.pages

        // При нажатии на героя, открываем окно с полной информацией о нем
        val heroAdapter = HeroAdapter(rickAndMorty.results, this, object : HeroAdapter.Callback {
            override fun onItemClicked(item: Results) {
                val action = HeroFragmentDirections.actionNavigationHeroToHeroInfo(
                    item.id,
                    item.name,
                    item.status,
                    item.species,
                    item.type,
                    item.gender,
                    item.origin.name,
                    item.location.name,
                    item.created,
                    item.image
                )
                Navigation.findNavController(root).navigate(action)
            }
        })

        // Скрываем прогресс бар
        // Проверяем ссылку на объект (Если попытаться быстро переключить фрагменты, то может вылететь ошибка без этого)
        rv_heroes?.let {
            it.adapter = heroAdapter
            pb_hero.visibility = GONE
        }
    }

    // Если не получили ответ апи
    override fun onDataErrorFromAPI(throwable: Throwable) {
        Toast.makeText(context, "Ошибка -> " + throwable.localizedMessage, LENGTH_SHORT).show()
    }
}