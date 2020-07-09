package com.ek.kotlinmvp.presentation.heroInfo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R
import kotlinx.android.synthetic.main.fragment_hero_info.*

class HeroInfoFragment : Fragment(), IHeroInfoView {

    // Аргументы, которые передаются из фрагмента HeroFragment (ид, имя и т.д)
    val args: HeroInfoFragmentArgs by navArgs()

    // Презентеры
    private lateinit var heroInfoPresenter: HeroInfoPresenter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация
        heroInfoPresenter = HeroInfoPresenter(IHeroInfoView = this)

        // Вставляем текст в текстбоксы, загружаем картинку
        Glide.with(view).load(args.heroImage).into(hero_info_image)
        hero_info_id.text = "Id: " + args.heroId.toString()
        hero_info_name.text = "Name: " + args.heroName
        hero_info_status.text = "Status: " + args.heroStatus
        hero_info_species.text = "Species: " + args.heroSpecies
        hero_info_type.text = "Type: " + args.heroType
        hero_info_gender.text = "Gender: " + args.heroGender
        hero_info_origin.text = "Origin: " + args.heroOrigin
        hero_info_location.text = "Location: " + args.heroLocation
        hero_info_created.text = "Created: " + args.heroCreated

        // Если герой жив, окрашиваем в зеленый, если нет, то в красный
        if (args.heroStatus == "Alive") hero_info_status.setTextColor(Color.GREEN)
        else if (args.heroStatus == "Dead") hero_info_status.setTextColor(Color.RED)

        // Кнопка открывающая полную инфу о персонаже
        b_hero_info_back.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_navigation_hero_info_to_navigation_hero)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_hero_info, container, false)

        return root
    }

}