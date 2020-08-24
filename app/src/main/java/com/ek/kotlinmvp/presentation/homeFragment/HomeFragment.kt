package com.ek.kotlinmvp.presentation.homeFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ek.kotlinmvp.R
import kotlinx.android.synthetic.main.fragment_home.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class HomeFragment : MvpAppCompatFragment(R.layout.fragment_home), IHomeView {

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

        val context: Context = requireActivity().applicationContext
        homePresenter.context = context

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        root = view

        homePresenter.getRecycler(rv_home_heroes)
    }

    override fun setRefreshing() {
        srl_home_heroes.setOnRefreshListener {
            homePresenter.resetData()
        }
    }

    override fun isRefreshed() {
        srl_home_heroes.isRefreshing = false
    }

    override fun openLoading() {
        pb_home.visibility = VISIBLE
    }

    override fun hideLoading() {
        pb_home.visibility = GONE
    }

    override fun setAdapter(heroDBAdapter: HeroDBAdapter) {
        rv_home_heroes.adapter = heroDBAdapter
    }

    override fun navigate(action: NavDirections) {
        Navigation.findNavController(root).navigate(action)
    }
}
