package dev.jk.zemoga.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import dev.jk.zemoga.R
import dev.jk.zemoga.databinding.FragmentPostsBinding
import dev.jk.zemoga.ui.posts.models.PostModel
import dev.jk.zemoga.ui.posts.adapters.PostsAdapter
import dev.jk.zemoga.ui.posts.viewmodels.PostsViewModel
import dev.jk.zemoga.ui.posts.viewmodels.viewstate.PostsViewState
import dev.jk.zemoga.utils.setItemDecorator

@AndroidEntryPoint
class PostsFragment : Fragment(), PostsAdapter.OnPostEventListener {

    private lateinit var binding: FragmentPostsBinding

    private val postsAdapter: PostsAdapter by lazy { PostsAdapter(this) }

    private val postsViewModel: PostsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onClickedPost(post: PostModel) {
        findNavController().navigate(
            R.id.action_postsFragment_to_detailFragment,
            bundleOf("selected-post" to post)
        )
    }

    override fun onSetFavoritePost(post: PostModel) {
        postsViewModel.setPostAsFavorite(post)
    }

    private fun init() {
        initializeObservers()
        initializeTabs()
        setupRefreshSwiper()
        setupFabIcon()
        initializeRecyclerView()
        postsViewModel.getAllPosts()
    }

    private fun initializeObservers() {
        postsViewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is PostsViewState.Success -> doOnSuccess(it.data)
                is PostsViewState.Error -> doOnError(it.message)
                is PostsViewState.Empty -> doOnEmpty()
            }
        })

    }

    private fun initializeRecyclerView() = with(binding.rvPosts) {
        adapter = postsAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        setItemDecorator()
        setHasFixedSize(true)
    }

    private fun setupFabIcon() {
        binding.fabDeletePosts.setOnClickListener {
            postsAdapter.clearItems()
            postsViewModel.deleteAllPosts()
            binding.tabsIndicator.visibility = View.GONE
        }
    }

    private fun doOnSuccess(data: List<PostModel>) {
        handleEmptyMessage(
            isVisible = data.isEmpty(),
            binding.tabsIndicator.getTabAt(1)?.isSelected
        )

        binding.tabsIndicator.let {
            if (it.visibility == View.GONE) it.visibility = View.VISIBLE
        }

        postsAdapter.addItems(data)
        binding.swipeToRefresh.isRefreshing = false
    }

    private fun doOnError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        handleEmptyMessage(isVisible = true, binding.tabsIndicator.getTabAt(1)?.isSelected)
    }

    private fun doOnEmpty() {
        binding.tabsIndicator.visibility = View.GONE
        handleEmptyMessage(isVisible = true, binding.tabsIndicator.getTabAt(1)?.isSelected)
    }

    private fun handleEmptyMessage(isVisible: Boolean, isFavoriteTabSelected: Boolean? = false) {
        binding.tvPostNonAvailable.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tvPostNonAvailable.text = requireContext().getString(
            if (isFavoriteTabSelected == false) R.string.posts_non_posts_available
            else R.string.posts_non_favorite_posts_available
        )
    }

    private fun initializeTabs() {
        binding.tabsIndicator.addTab(
            binding.tabsIndicator.newTab()
                .setText(requireContext().getString(R.string.posts_btn_all))
        )
        binding.tabsIndicator.addTab(
            binding.tabsIndicator.newTab()
                .setText(requireContext().getString(R.string.posts_btn_favorites))
        )
        binding.tabsIndicator.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                postsViewModel.let {
                    if (tab.position == 0) {
                        binding.swipeToRefresh.isEnabled = true
                        it.getAllPosts()
                    } else {
                        binding.swipeToRefresh.isEnabled = false
                        it.getFavoritePosts()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) = Unit

            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })
    }

    private fun setupRefreshSwiper() {
        binding.swipeToRefresh.setOnRefreshListener {
            postsViewModel.getAllPosts()
        }

        binding.swipeToRefresh.setColorSchemeResources(R.color.primaryGreen)
    }

}