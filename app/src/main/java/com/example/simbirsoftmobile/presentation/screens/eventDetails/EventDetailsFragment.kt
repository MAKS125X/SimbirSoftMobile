package com.example.simbirsoftmobile.presentation.screens.eventDetails

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.simbirsoftmobile.R
import com.example.simbirsoftmobile.databinding.FragmentEventDetailsBinding
import com.example.simbirsoftmobile.presentation.models.event.Event
import com.example.simbirsoftmobile.presentation.screens.utils.getRemainingDateInfo
import com.example.simbirsoftmobile.repository.EventRepository

class EventDetailsFragment : Fragment() {
    private var _binding: FragmentEventDetailsBinding? = null
    private val binding: FragmentEventDetailsBinding
        get() = _binding!!

    private var eventId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            eventId = it.getInt(EVENT_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val event = eventId?.let { getEventById(it) }
        if (event == null) {
            Toast.makeText(requireContext(), "Event with id: $eventId is null", Toast.LENGTH_LONG)
                .show()
        } else {
            binding.titleTV.text = event.title
            binding.organizerNameTV.text = event.organizerName
            binding.addressTV.text = event.address

            binding.remainDateTV.text = getRemainingDateInfo(event.dateStart, event.dateEnd)

            initEmailSection(event.email)
            initPhoneNumbers(event.phoneNumbers)
            initImage(event.imageUrl)

            binding.descriptionTV.text = event.description
            initOrganizerUrlText(event.siteUrl)

            binding.toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }

            binding.toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share_event -> {
                        val shareIntent =
                            Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, event.title)
                                type = "text/plain"
                            }
                        startActivity(Intent.createChooser(shareIntent, "Поделиться событием"))
                    }
                }
                true
            }
        }
    }

    private fun getEventById(id: Int): Event? {
        return eventId?.let { EventRepository.getEventById(id, requireContext()) }
    }

    private fun initEmailSection(email: String) {
        if (email.isNotBlank()) {
            binding.emailLayout.visibility = View.VISIBLE
            binding.sendEmailTV.setOnClickListener {
                sendEmail(email)
            }
        } else {
            binding.emailLayout.visibility = View.GONE
        }
    }

    private fun sendEmail(email: String) {
        val subject = "Благотворительная помощь"
        val intent =
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun initPhoneNumbers(phoneNumbers: List<String>) {
        if (phoneNumbers.isNotEmpty()) {
            binding.emailLayout.visibility = View.VISIBLE
            binding.phoneTV.text = phoneNumbers.joinToString("\n")
        } else {
            binding.emailLayout.visibility = View.GONE
        }
    }

    private fun initImage(imageResource: Int) {
        try {
            val drawable = ContextCompat.getDrawable(requireContext(), imageResource)
            if (drawable != null) {
                binding.previewIV.visibility = View.VISIBLE
                binding.previewIV.setImageDrawable(drawable)
            } else {
                binding.previewIV.visibility = View.GONE
            }
        } catch (e: Resources.NotFoundException) {
            binding.previewIV.visibility = View.GONE
        }
    }

    private fun initOrganizerUrlText(siteUrl: String) {
        if (siteUrl.isNotBlank()) {
            binding.organizerUrlTV.visibility = View.VISIBLE
            binding.organizerUrlTV.setOnClickListener {
                openLink(siteUrl)
            }
        } else {
            binding.organizerUrlTV.visibility = View.GONE
        }
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    companion object {
        const val TAG = "EventDetailsFragment"
        private const val EVENT_ID_KEY = "EventId"

        @JvmStatic
        fun newInstance(eventId: Int) =
            EventDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(EVENT_ID_KEY, eventId)
                }
            }
    }
}
