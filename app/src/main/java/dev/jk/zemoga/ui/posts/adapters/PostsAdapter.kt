package dev.jk.zemoga.ui.posts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.jk.zemoga.R
import dev.jk.zemoga.databinding.ItemPostBinding
import dev.jk.zemoga.ui.posts.models.PostModel

class PostsAdapter(
    private val listener: OnPostEventListener
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    interface OnPostEventListener {
        fun onClickedPost(post: PostModel)
        fun onSetFavoritePost(post: PostModel)
    }

    private val items = arrayListOf<PostModel>()

    fun addItems(entries: List<PostModel>) {
        items.clear()
        items.addAll(entries)
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: ItemPostBinding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    private fun updateItemState(position: Int) {
        notifyItemChanged(position)
    }

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PostModel) = with(binding) {

            tvPostTitle.text = model.title
            tvPostDesc.text = model.body

            ivFavoriteIcon.setImageResource(
                getDrawableIcon(model.isFavorite)
            )

            setupHolderEvents(model)
        }

        private fun getDrawableIcon(isFavorite: Boolean) =
            if (isFavorite) R.drawable.ic_filled_star
            else R.drawable.ic_outlined_star

        private fun setupHolderEvents(model: PostModel) = with(binding) {
            root.setOnClickListener {
                listener.onClickedPost(model)
            }

            ivFavoriteIcon.setOnClickListener {
                model.isFavorite = !model.isFavorite
                listener.onSetFavoritePost(model)
                updateItemState(bindingAdapterPosition)
            }
        }
    }

}