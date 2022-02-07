package dev.jk.zemoga.ui.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.jk.zemoga.R
import dev.jk.zemoga.databinding.FragmentDetailBinding
import dev.jk.zemoga.ui.postdetail.models.AuthorModel
import dev.jk.zemoga.ui.postdetail.models.CommentModel
import dev.jk.zemoga.ui.posts.models.PostModel
import dev.jk.zemoga.ui.postdetail.adapters.CommentsAdapter
import dev.jk.zemoga.ui.postdetail.viewmodels.DetailedPostViewModel
import dev.jk.zemoga.ui.postdetail.viewmodels.viewstate.DetailedPostViewState
import dev.jk.zemoga.utils.setItemDecorator


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding

    private val commentsAdapter: CommentsAdapter by lazy {
        CommentsAdapter()
    }

    private val detailedPostViewModel: DetailedPostViewModel by viewModels()

    private lateinit var post: PostModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<PostModel>("selected-post")?.let {
            post = it
            setupPostInfo(it)
            detailedPostViewModel.getPostDetailedInfo(it)
        }

        init()
    }

    private fun init() {
        initializeObservers()
        initializeRecyclerView()
        setupFab()
    }

    private fun initializeRecyclerView() = with(binding.rvPostComments){
        adapter = commentsAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        setItemDecorator()
        setHasFixedSize(true)
    }

    private fun initializeObservers() {
        detailedPostViewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is DetailedPostViewState.Success -> doOnSuccess(it.data)
                is DetailedPostViewState.Loading -> doOnLoading(it.isLoading)
                is DetailedPostViewState.Error -> Unit
            }
        })
    }

    private fun setupFab() {
        updateFabIcon()

        binding.fabSetPostAsFavorite.setOnClickListener {
            if (this::post.isInitialized) {
                post.isFavorite = !post.isFavorite
                detailedPostViewModel.setPostAsFavorite(post)
                updateFabIcon()
            }
        }
    }

    private fun updateFabIcon() {
        binding.fabSetPostAsFavorite.setImageResource(
            if (post.isFavorite) R.drawable.ic_baseline_star_24
            else R.drawable.ic_baseline_star_border_24
        )
    }

    private fun setupPostInfo(post: PostModel) {
        binding.tvPostTitle.text = post.title
        binding.tvPostDescriptionContent.text = post.body
    }

    private fun doOnLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progress.visibility = View.VISIBLE
            binding.gDetailedPostViewItems.visibility = View.GONE
            binding.fabSetPostAsFavorite.visibility = View.GONE
        } else {
            binding.progress.visibility = View.GONE
            binding.gDetailedPostViewItems.visibility = View.VISIBLE
            binding.fabSetPostAsFavorite.visibility = View.VISIBLE

        }
    }

    private fun doOnSuccess(data: Pair<List<CommentModel>, AuthorModel>) {
        binding.tvPostAuthorName.text = data.second.name
        binding.tvPostAuthorEmail.text = data.second.email
        binding.tvPostAuthorPhone.text = data.second.phone
        binding.tvPostAuthorWebsite.text = data.second.website

        commentsAdapter.addItems(data.first)
    }

}