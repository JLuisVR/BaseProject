package com.vrjoseluis.baseproject.ui.userlist;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vrjoseluis.baseproject.R
import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import com.vrjoseluis.baseproject.databinding.FragmentUserListBinding
import com.vrjoseluis.baseproject.ui.userdetail.UserDetailFragment.Companion.EXTRA_USER_ID
import com.vrjoseluis.baseproject.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment(), UserAdapter.OnItemClickListener {

    private var binding: FragmentUserListBinding? = null
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.userListInputSearch?.doOnTextChanged { text, start, before, count ->
            viewModel.filterUserListByName(text.toString())
        }
        binding?.userListButtonAdd?.setOnClickListener {
            navigateToUserDetail()
        }
        setupRecyclerView()
        setupObservers()
        viewModel.requestCharacterList()
    }

    override fun onItemClick(position: Int, item: User) {
        navigateToUserDetail(item.id)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setupObservers() {
        viewModel.getUserListLiveData().observe(viewLifecycleOwner) { result ->
            binding?.userListLoading?.isVisible = result is AsyncResult.Loading
            if (result is AsyncResult.Success) {
                val userList = result.data ?: emptyList()
                setupUserList(userList)
            } else if (result is AsyncResult.Error) {
                context?.showToast(context?.getString(R.string.error_get_user_list))
            }
        }
    }

    private fun setupUserList(userList: List<User>) {
        (binding?.userListRecycler?.adapter as? UserAdapter)?.submitList(userList)
        binding?.userListRecycler?.isVisible = userList.isNotEmpty()
        binding?.userListLabelEmpty?.isVisible = userList.isEmpty()
    }

    private fun setupRecyclerView() {
        binding?.userListRecycler?.adapter = UserAdapter(this)
    }

    private fun navigateToUserDetail(userId: Int? = null) {
        findNavController().navigate(
            R.id.action_user_list_to_user_detail,
            bundleOf(EXTRA_USER_ID to userId)
        )
    }
}
