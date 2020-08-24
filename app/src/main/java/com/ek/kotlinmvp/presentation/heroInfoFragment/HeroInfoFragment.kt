package com.ek.kotlinmvp.presentation.heroInfoFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R
import kotlinx.android.synthetic.main.fragment_hero_info.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HeroInfoFragment : MvpAppCompatFragment(R.layout.fragment_hero_info), IHeroInfoView {

    // Аргументы, которые передаются из фрагмента HeroFragment (ид, имя и т.д)
    private val args: HeroInfoFragmentArgs by navArgs()

    // Презентеры
    @InjectPresenter
    lateinit var heroInfoPresenter: HeroInfoPresenter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Вставляем текст в текстбоксы, загружаем картинку
        Glide.with(requireActivity().applicationContext).load(args.heroImage).into(hero_info_image)
        hero_info_id.text = "Id: ${args.heroId}"
        hero_info_name.text = "Name: ${args.heroName}"
        hero_info_status.text = "Status: ${args.heroStatus}"
        hero_info_species.text = "Species: ${args.heroSpecies}"
        hero_info_type.text = "Type: ${args.heroType}"
        hero_info_gender.text = "Gender: ${args.heroGender}"
        hero_info_origin.text = "Origin: ${args.heroOrigin}"
        hero_info_location.text = "Location: ${args.heroLocation}"
        hero_info_created.text = "Created: ${args.heroCreated}"

        // Если герой жив, окрашиваем в зеленый, если нет, то в красный
        if (args.heroStatus == "Alive") hero_info_status.setTextColor(Color.GREEN)
        else if (args.heroStatus == "Dead") hero_info_status.setTextColor(Color.RED)

        // Кнопка открывающая полную инфу о персонаже
        b_hero_info_back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}