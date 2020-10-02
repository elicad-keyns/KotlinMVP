package com.ek.kotlinmvp.presentation.heroInfoFragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ek.kotlinmvp.R.*
import com.ek.kotlinmvp.R.string.infoId
import com.ek.kotlinmvp.common.Status
import com.ek.kotlinmvp.common.StatusColor
import kotlinx.android.synthetic.main.fragment_hero_info.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HeroInfoFragment : MvpAppCompatFragment(layout.fragment_hero_info), IHeroInfoView {

    // Аргументы, которые передаются из фрагмента HeroFragment (ид, имя и т.д)
    private val args: HeroInfoFragmentArgs by navArgs()

    // Презентеры
    @InjectPresenter
    lateinit var heroInfoPresenter: HeroInfoPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Вставляем текст в текстбоксы, загружаем картинку
        Glide.with(requireActivity().applicationContext).load(args.heroImage).into(heroInfoImage)
        heroInfoId.text = String.format(getString(infoId) + args.heroId)
        heroInfoName.text = String.format(getString(string.infoName) + args.heroName)
        heroInfoStatus.text = String.format(getString(string.infoStatus) + args.heroStatus)
        heroInfoSpecies.text = String.format(getString(string.infoSpecies) + args.heroSpecies)
        heroInfoType.text = String.format(getString(string.infoType) + args.heroType)
        heroInfoGender.text = String.format(getString(string.infoGender) + args.heroGender)
        heroInfoOrigin.text = String.format(getString(string.infoOrigin) + args.heroOrigin)
        heroInfoLocation.text = String.format(getString(string.infoLocation) + args.heroLocation)
        heroInfoCreated.text = String.format(getString(string.infoCreated) + args.heroCreated)

        // Если герой жив, окрашиваем в зеленый, если нет, то в красный
        when (args.heroStatus) {
            Status.Alive.name -> heroInfoStatus.setTextColor( StatusColor.AliveColor.color )
            Status.Dead.name -> heroInfoStatus.setTextColor( StatusColor.DeadColor.color )
            Status.unknown.name -> heroInfoStatus.setTextColor( StatusColor.UnknownColor.color )
        }

        b_hero_info_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }
}