package com.ek.kotlinmvp.presentation.heroInfoFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.other.MainApplication
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
        Glide.with(requireActivity().applicationContext).load(args.heroImage).into(heroInfoImage)
        heroInfoId.text = "Id: ${args.heroId}"
        heroInfoName.text = "Name: ${args.heroName}"
        heroInfoStatus.text = "Status: ${args.heroStatus}"
        heroInfoSpecies.text = "Species: ${args.heroSpecies}"
        heroInfoType.text = "Type: ${args.heroType}"
        heroInfoGender.text = "Gender: ${args.heroGender}"
        heroInfoOrigin.text = "Origin: ${args.heroOrigin}"
        heroInfoLocation.text = "Location: ${args.heroLocation}"
        heroInfoCreated.text = "Created: ${args.heroCreated}"

        // Если герой жив, окрашиваем в зеленый, если нет, то в красный
        when (args.heroStatus) {
            "Alive" -> heroInfoStatus.setTextColor(
                ContextCompat.getColor(
                    MainApplication.context,
                    R.color.colorAccent
                )
            )
            "Dead" -> heroInfoStatus.setTextColor(
                ContextCompat.getColor(
                    MainApplication.context,
                    R.color.colorVinous
                )
            )
        }

        b_hero_info_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }
}