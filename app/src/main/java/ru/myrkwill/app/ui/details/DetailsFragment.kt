package ru.myrkwill.app.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.myrkwill.app.R
import ru.myrkwill.app.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    lateinit var binding: FragmentDetailsBinding
    private val fragmentArgs: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        val article = fragmentArgs.article
        titleTextView.text = article.title
        descriptionTextView.text = article.description
        Glide.with(this@DetailsFragment)
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_broken_image)
            .into(headerImageView)
        headerImageView.clipToOutline = true

        backImageView.setOnClickListener { findNavController().popBackStack() }
        visitSiteButton.setOnClickListener {
            try {
                Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }
                        ?.let { article.url }
                        ?: "https://google.com" ))
                        .let { ContextCompat.startActivity(requireContext(), it, null) }
            } catch (e: java.lang.Exception) {
                Toast.makeText(context, "The device doesn't have any browser to view the document!", Toast.LENGTH_SHORT)
            }
        }
    }
}