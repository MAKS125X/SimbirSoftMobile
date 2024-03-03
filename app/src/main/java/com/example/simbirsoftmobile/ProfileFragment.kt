package com.example.simbirsoftmobile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simbirsoftmobile.databinding.FragmentEditPhotoDialogBinding
import com.example.simbirsoftmobile.databinding.FragmentProfileBinding
import com.example.simbirsoftmobile.models.FriendUI
import java.io.File

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding!!

    private val adapter: FriendAdapter by lazy { FriendAdapter(testList) }

    private val profilePhotoFile: File
        get() {
            val tempImagesDir = File(
                requireContext().filesDir,
                getString(R.string.temp_images_dir)
            )
            tempImagesDir.mkdir()

            return File(
                tempImagesDir,
                getString(R.string.temp_image)
            )
        }

    private val profilePhotoUri: Uri
        get() = FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.authorities),
            profilePhotoFile
        )

    private val takePhotoForResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it != null && it) {
                // Сброс ссылки, так как иначе обновление
                // происходит только при перезапуске приложения
                binding.profileIV.setImageURI(null)

                binding.profileIV.setImageURI(profilePhotoUri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (profilePhotoFile.exists()) {
            binding.profileIV.setImageURI(profilePhotoUri)
        }

        binding.profileIV.setOnClickListener {
            showEditPhotoDialog()
        }

        initAdapter()
    }

    private fun showEditPhotoDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentEditPhotoDialogBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)

        val dialog: AlertDialog = builder.create()

        dialogBinding.pickPhotoLayout.setOnClickListener {

        }
        dialogBinding.takePictureLayout.setOnClickListener {
            takePhotoForResult.launch(profilePhotoUri)
            dialog.dismiss()
        }
        dialogBinding.deletePictureLayout.setOnClickListener {
            profilePhotoFile.delete()
            binding.profileIV.setImageResource(R.drawable.image_man)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun initAdapter() {
        binding.friendRecycler.addItemDecoration(FriendAdapter.CustomItemDecoration())
        binding.friendRecycler.adapter = adapter
        binding.friendRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
        const val TAG = "ProfileFragment"

        private val testList =
            arrayOf(
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Дмитрий Валерьевич", R.drawable.avatar_3),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Евгений Александров", R.drawable.avatar_2),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
                FriendUI("Виктор Кузнецов", R.drawable.avatar_1),
            )
    }
}