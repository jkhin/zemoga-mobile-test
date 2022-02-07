package dev.jk.zemoga.ui.postdetail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.jk.zemoga.R
import dev.jk.zemoga.databinding.ItemCommentBinding
import dev.jk.zemoga.ui.postdetail.models.CommentModel

class CommentsAdapter(
) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {


    private val items = arrayListOf<CommentModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(entries: List<CommentModel>) {
        items.clear()
        items.addAll(entries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding: ItemCommentBinding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CommentViewHolder(
        private val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CommentModel) = with(binding) {
            tvCommentDesc.text = model.body
            tvCommentByUserName.text =
                root.context.getString(R.string.detail_post_comment_by_name, model.name)
            tvCommentByEmail.text =
                root.context.getString(R.string.detail_post_comment_by_email, model.email)

        }
    }

}