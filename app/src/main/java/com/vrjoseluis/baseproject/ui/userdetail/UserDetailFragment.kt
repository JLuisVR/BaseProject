package com.vrjoseluis.baseproject.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vrjoseluis.baseproject.R
import com.vrjoseluis.baseproject.data.model.User
import com.vrjoseluis.baseproject.data.repository.utils.AsyncResult
import com.vrjoseluis.baseproject.databinding.FragmentUserDetailBinding
import com.vrjoseluis.baseproject.util.showToast
import com.vrjoseluis.baseproject.util.toDate
import com.vrjoseluis.baseproject.util.toString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    companion object {
        const val EXTRA_USER_ID = "userId"
        private const val BIRTHDATE_FORMAT = "dd/MM/yyyy"
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
            getUserFromForm()?.let { viewModel.saveUser(it) }
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

    private fun validateForm(): Boolean {
        var valid = true
        val name = binding?.userDetailInputName?.editText?.text.toString()
        val birthdate = binding?.userDetailInputBirthdate?.editText?.text.toString()
        if (name.isBlank()) {
            binding?.userDetailInputName?.error = context?.getString(R.string.mandatory_field)
            binding?.userDetailInputName?.isErrorEnabled = true
            valid = false
        } else {
            binding?.userDetailInputName?.isErrorEnabled = false
        }

        if (birthdate.isBlank()) {
            binding?.userDetailInputBirthdate?.error = context?.getString(R.string.mandatory_field)
            binding?.userDetailInputBirthdate?.isErrorEnabled = true
            valid = false
        } else if (birthdate.toDate(BIRTHDATE_FORMAT) == null) {
            binding?.userDetailInputBirthdate?.error = context?.getString(R.string.invalid_format)
            binding?.userDetailInputBirthdate?.isErrorEnabled = true
            valid = false
        } else {
            binding?.userDetailInputBirthdate?.isErrorEnabled = false
        }
        return valid
    }

    private fun getUserFromForm(): User? {
        return if (validateForm()) {
            User(
                userId.takeIf { it > 0 },
                binding?.userDetailInputName?.editText?.text?.toString().orEmpty(),
                binding?.userDetailInputBirthdate?.editText?.text?.toString()
                    ?.toDate(BIRTHDATE_FORMAT),
            )
        } else {
            null
        }
    }

    private fun setUserDataToForm(user: User) {
        binding?.userDetailInputName?.editText?.setText(user.name)
        binding?.userDetailInputBirthdate?.editText?.setText(
            user.birthdate?.toString(
                BIRTHDATE_FORMAT
            )
        )
    }

    private fun setupObservers() {
        viewModel.getUserDetailLiveData().observe(viewLifecycleOwner) { result ->
            binding?.userDetailLoading?.isVisible = result is AsyncResult.Loading
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
                if (userId == 0) {
                    findNavController().navigateUp()
                }
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