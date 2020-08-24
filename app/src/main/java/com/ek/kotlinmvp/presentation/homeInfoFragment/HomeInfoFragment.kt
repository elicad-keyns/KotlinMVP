package com.ek.kotlinmvp.presentation.homeInfoFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.ViewTarget
import com.ek.kotlinmvp.R
import kotlinx.android.synthetic.main.fragment_home_info.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HomeInfoFragment : MvpAppCompatFragment(R.layout.fragment_home_info), IHomeInfoView {

    // Аргументы, которые передаются из фрагмента HomeFragment (ид, имя и т.д)
    private val args: HomeInfoFragmentArgs by navArgs()

    @InjectPresenter
    lateinit var homeInfoPresenter: HomeInfoPresenter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Вставляем текст в текстбоксы, загружаем картинку
        Glide.with(requireActivity().applicationContext).load(args.heroImage).into(home_info_image)
        home_info_id.text       = "Id: ${args.heroId}"
        home_info_name.text     = "Name: ${args.heroName}"
        home_info_status.text   = "Status: ${args.heroStatus}"
        home_info_species.text  = "Species: ${args.heroSpecies}"
        home_info_type.text     = "Type: ${args.heroType}"
        home_info_gender.text   = "Gender: ${args.heroGender}"
        home_info_origin.text   = "Origin: ${args.heroOrigin}"
        home_info_location.text = "Location: ${args.heroLocation}"
        home_info_created.text  = "Created: ${args.heroCreated}"

        // Если герой жив, окрашиваем в зеленый, если нет, то в красный
        if (args.heroStatus == "Alive") home_info_status.setTextColor(Color.GREEN)
        else if (args.heroStatus == "Dead") home_info_status.setTextColor(Color.RED)

        // Кнопка открывающая полную инфу о персонаже
        b_home_info_back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}