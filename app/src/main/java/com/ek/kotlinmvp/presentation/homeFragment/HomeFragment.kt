package com.ek.kotlinmvp.presentation.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.common.LoadStatus
import com.ek.kotlinmvp.common.LoadStatus.*
import com.ek.kotlinmvp.data.db.entity.Hero
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HomeFragment : MvpAppCompatFragment(R.layout.fragment_home), IHomeView {

    @InjectPresenter
    lateinit var homePresenter: HomePresenter
    private lateinit var heroDBAdapter: HeroAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        createAdapter(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHomeHeroes.addCustomScrollListener(1) {
            if (!homePresenter.isLoading) {
                heroDBAdapter.insertLoader()
                homePresenter.onLoadMore()
            }
        }
        setAdapter(heroDBAdapter)
    }

    override fun createAdapter(view: View) {
        heroDBAdapter = HeroAdapter(
            adapterCallback = object : AdapterCallback {
                override fun onItemClicked(item: Hero) {
                    val action = HomeFragmentDirections.actionNavigationHomeToNavigationHeroInfo2(
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
                    Navigation.findNavController(view).navigate(action)
                }
            })
    }

    override fun addHeroes(_heroes: ArrayList<Hero?>) {
        heroDBAdapter.insert(_heroes)
    }

    override fun setRefreshing() {
        srl_home_heroes.setOnRefreshListener {
            homePresenter.resetData(heroDBAdapter)
        }
    }

    override fun isRefresh(loadStatus: LoadStatus) {
        when (loadStatus) {
            On -> srl_home_heroes.isRefreshing = true
            Off -> srl_home_heroes.isRefreshing = false
        }
    }

    override fun setAdapter(heroDBAdapter: HeroAdapter) {
        rvHomeHeroes.adapter = heroDBAdapter
    }
}