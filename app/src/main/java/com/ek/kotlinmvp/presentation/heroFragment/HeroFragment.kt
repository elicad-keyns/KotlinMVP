package com.ek.kotlinmvp.presentation.heroFragment

import Results
import RickAndMorty
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ek.kotlinmvp.R
import kotlinx.android.synthetic.main.fragment_hero.*

class HeroFragment : Fragment(), IRAMView {

  // Максимальное кол-во страниц
  var maxPages: Int? = null

  // Презентеры
  private lateinit var ramPresenter: RAMPresenter

  // Вьюшка
  private lateinit var root: View

  // Элементы вьюшки (кнопки, текствью)
  lateinit var b_next: Button
  lateinit var b_prev: Button

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    root = inflater.inflate(R.layout.fragment_hero, container, false)

    var page: Int = 1

    // Инициализация
    b_next = root.findViewById(R.id.b_next_page)
    b_prev = root.findViewById(R.id.b_prev_page)
    ramPresenter = RAMPresenter(this)

    // Получаем данные с апи
    ramPresenter.getDataFromAPI(page = page)

    // Обработчик для переключения страниц назад
    b_prev.setOnClickListener {
      if (page > 1) {
        page--
        tv_page.text = page.toString()
        ramPresenter.getDataFromAPI(page = page)
      }
    }

    // Обработчик для переключений страниц вперед
    b_next.setOnClickListener {
      if (page < maxPages!!) {
        page++
        tv_page.text = page.toString()
        ramPresenter.getDataFromAPI(page = page)
      }
    }

    return root
  }

  override fun onDataCompleteFromAPI(rickAndMorty: RickAndMorty) {
    if (maxPages == null)
      maxPages = rickAndMorty.info.pages

    val heroAdapter = HeroAdapter(rickAndMorty.results, this, object : HeroAdapter.Callback {
        override fun onItemClicked(item: Results) {
          //TODO Here comes element, that was clicked on. You can continue to work with it.
        }
      })

    // Проверяем ссылку на объект (Если попытаться быстро переключить фрагменты, то может вылететь ошибка без этого)
    rv_heroes?.let {
      it.adapter = heroAdapter
    }
  }

  override fun onDataErrorFromAPI(throwable: Throwable) {
    TODO("Not yet implemented")
  }
}