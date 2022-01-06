package com.vrjoseluis.baseproject.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vrjoseluis.baseproject.R
import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import com.vrjoseluis.baseproject.databinding.FragmentUserDetailBinding
import com.vrjoseluis.baseproject.ui.MainActivity
import com.vrjoseluis.baseproject.util.getDate
import com.vrjoseluis.baseproject.util.setDate
import com.vrjoseluis.baseproject.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    companion object {
        const val EXTRA_USER_ID = "userId"
    }

    private var binding: FragmentUserDetailBinding? = null
    private val viewModel: UserDetailViewModel by viewModels()
    private val userId by lazy { arguments?.getInt(EXTRA_USER_ID, 0) ?: 0 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.userDetailBtnDelete?.isVisible = userId > 0
        binding?.userDetailBtnDelete?.setOnClickListener {
            viewModel.deleteUser(userId)
        }
        binding?.userDetailBtnSave?.setOnClickListener {
            viewModel.saveUser(getUserFromForm())
        }
        setupObservers()

        if (userId > 0) {
            viewModel.requestUserDetail(userId)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun getUserFromForm(): User {
        return User(
            null,
            binding?.userDetailInputName?.text?.toString().orEmpty(),
            binding?.userDetailDatePickerBirthdate?.getDate()
        )
    }

    private fun setUserDataToForm(user: User) {
        binding?.userDetailInputName?.setText(user.name)
        binding?.userDetailDatePickerBirthdate?.setDate(user.birthdate)
    }

    private fun setupObservers() {
        viewModel.getUserDetailLiveData().observe(viewLifecycleOwner) { result ->
            val userDetail = result?.data
            if (result is AsyncResult.Success && userDetail != null) {
                setUserDataToForm(userDetail)
            } else if (result is AsyncResult.Error) {
                context?.showToast(context?.getString(R.string.error_get_user_detail))
            }
        }

        viewModel.getSaveUserLiveData().observe(viewLifecycleOwner) { result ->
            binding?.userDetailLoading?.isVisible = result is AsyncResult.Loading
            if (result is AsyncResult.Success) {
                context?.showToast(context?.getString(R.string.user_save_msg))
            } else if (result is AsyncResult.Error) {
                context?.showToast(context?.getString(R.string.error_get_user_detail))
            }
        }

        viewModel.getDeleteUserLiveData().observe(viewLifecycleOwner) { result ->
            binding?.userDetailLoading?.isVisible = result is AsyncResult.Loading
            if (result is AsyncResult.Success) {
                context?.showToast(context?.getString(R.string.user_delete_msg))
                findNavController().navigateUp()
            } else if (result is AsyncResult.Error) {
                context?.showToast(context?.getString(R.string.error_get_user_detail))
            }
        }
    }
}