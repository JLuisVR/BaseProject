package com.vrjoseluis.baseproject.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.databinding.RowUserBinding
import com.vrjoseluis.baseproject.ui.userlist.UserAdapter.UserViewHolder
import com.vrjoseluis.baseproject.util.toString

class UserAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<User, UserViewHolder>(DiffCallback()) {

    companion object{
        private const val BIRTHDATE_FORMAT = "dd/MM/YYYY"
    }
    private class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name && oldItem.birthdate?.toString(BIRTHDATE_FORMAT) ==  newItem.birthdate?.toString(BIRTHDATE_FORMAT)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    inner class UserViewHolder(private val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User, clickListener: OnItemClickListener) {
            binding.rowUserLabelName.text = item.name
            binding.rowUserLabelBirthdate.text = item.birthdate?.toString(BIRTHDATE_FORMAT)
            itemView.setOnClickListener { clickListener.onItemClick(adapterPosition, item) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: User)
    }
}