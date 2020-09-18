package com.ek.kotlinmvp.presentation.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ek.kotlinmvp.R
import com.ek.kotlinmvp.data.db.entity.Hero
import com.ek.kotlinmvp.other.MainApplication
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HomeFragment : MvpAppCompatFragment(R.layout.fragment_home), IHomeView{

    var isLoading: Boolean = false

    // Адаптер
    private lateinit var heroDBAdapter: HeroDBAdapter

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

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        root = view
    }

    override fun setScrollListener() {

        val visibleThreshold = 1
        var lastVisibleItem: Int
        var totalItemCount: Int

        val linearLayoutManager: LinearLayoutManager =
            rvHomeHeroes.layoutManager as LinearLayoutManager
        rvHomeHeroes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                Toast.makeText(MainApplication.context, "Всего: $totalItemCount\nЛаст: $lastVisibleItem", Toast.LENGTH_SHORT).show()
                if (!isLoading && visibleThreshold >= totalItemCount - lastVisibleItem) {
                    homePresenter.getNewHeroes()

                    //Test
                    isLoading = true
                }
            }
        })
    }

    override fun isLoaded() {
        isLoading = false
    }

    override fun createAdapter() {
        heroDBAdapter = HeroDBAdapter(
            ArrayList(),
            object : HeroDBAdapter.Callback {
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
                    Navigation.findNavController(root).navigate(action)
                }
            })

        setAdapter(heroDBAdapter)
    }

    override fun addHeroes(_heroes: ArrayList<Hero>) {
        heroDBAdapter.insert(_heroes)
    }

    override fun setRefreshing() {
        srl_home_heroes.setOnRefreshListener {
            homePresenter.resetData(heroDBAdapter)
        }
    }

    override fun isRefreshed() {
        srl_home_heroes.isRefreshing = false
    }

    override fun isRefreshing() {
        srl_home_heroes.isRefreshing = true
    }

    override fun setAdapter(heroDBAdapter: HeroDBAdapter) {
        rvHomeHeroes.adapter = heroDBAdapter
    }
}
