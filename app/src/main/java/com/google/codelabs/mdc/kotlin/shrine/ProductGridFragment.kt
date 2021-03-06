package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter
import kotlinx.android.synthetic.main.shr_backdrop.view.*
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.*
import timber.log.Timber

class ProductGridFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.shr_product_grid_fragment, container, false)

        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)
        view.app_bar.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                view.product_grid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.shr_branded_menu), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu))) // Menu close icon

        view.recycler_view.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        view.recycler_view.layoutManager = gridLayoutManager
        val adapter = StaggeredProductCardRecyclerViewAdapter(
                ProductEntry.initProductEntryList(resources))
        view.recycler_view.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small)
        view.recycler_view.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))

        // Set cut corner background for API 23+
        view.product_grid.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)

        view.home_switcher.setOnClickListener{
            //view.featured_label.setTextColor(ContextCompat.getColor(context!!,R.color.test))
            Timber.d("FUCK")
            (activity as NavigationHost).navigateTo(FeaturedFragment(),false)
        }

        view.command_mode_switcher.setOnClickListener {
            Timber.d("FUCK2")
            (activity as NavigationHost).navigateTo(TestFragment(),false)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        Timber.d("Hello")
        menuInflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }
}
