package com.example.shopsphere.features.products.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.size.Dimension
import com.example.shopsphere.databinding.FragmentAllProdutsBinding
import com.example.shopsphere.features.categories.presentation.adatper.CategoriesAdapter
import com.example.shopsphere.features.categories.presentation.mvi.CategoriesIntents
import com.example.shopsphere.features.categories.presentation.mvi.CategoriesState
import com.example.shopsphere.features.categories.presentation.mvi.CategoriesViewModel
import com.example.shopsphere.features.products.presentation.mvi.ProductsViewModel
import com.example.shopsphere.features.products.presentation.mvi.ProductsState
import com.example.shopsphere.features.products.presentation.mvi.UserIntents
import com.example.shopsphere.features.products.presentation.ui.adapter.ProductsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProductsFragment : Fragment(), CategoriesAdapter.OnCategoryClicked {
	private lateinit var _binding: FragmentAllProdutsBinding
	private val binding
		get() = _binding
	private val productsViewModel: ProductsViewModel by viewModels()
	private val categoriesViewModel: CategoriesViewModel by viewModels()
	private lateinit var productsAdapter: ProductsAdapter
	private lateinit var categoriesAdapter: CategoriesAdapter
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		productsAdapter = ProductsAdapter()
		categoriesAdapter = CategoriesAdapter(this)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentAllProdutsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupContainers()
		renderStates()
		sendIntents()
	}

	private fun setupContainers() {
		setupCategoriesContainer()
		setupProductsContainer()
	}

	private fun sendIntents() {
		lifecycleScope.launch {
			categoriesIntentSend()
			productsIntentSend()
			searchForProduct()
		}
	}

	private fun renderStates() {
		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				launch { categoriesStateRender() }
				launch { productsStateRender() }
				searchForProductsRender()
				categoryProductsIntentRender()
			}
		}
	}

	private suspend fun productsIntentSend() {
		productsViewModel.userIntentChannel.send(UserIntents.LoadProducts)
	}

	private suspend fun productsStateRender() {
		productsViewModel.state.collect { uiState ->
			when (uiState) {
				is ProductsState.Failure -> {
					Log.d("TAG", "renderState: ${uiState.e.message}")
					/**
					 * show snackbar with error
					 */
					binding.productsLoader.visibility = View.GONE
					Snackbar.make(
						requireView(),
						uiState.e.message.toString(),
						Snackbar.LENGTH_SHORT
					)
				}

				ProductsState.Loading    -> {
					/**
					 * show progress indicator
					 */
					binding.productsLoader.visibility = View.VISIBLE
				}

				is ProductsState.Success -> {
					val products = uiState.products
					productsAdapter.submitList(products)
					binding.productsLoader.visibility = View.GONE
				}
			}
		}
	}

	private fun setupProductsContainer() {
		binding.rvProductsContainer.apply {
			setHasFixedSize(true)
			layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
			adapter = productsAdapter
		}
	}

	private suspend fun categoriesIntentSend() {
		categoriesViewModel.userIntentChannel.send(CategoriesIntents.GetCategories)
	}

	private suspend fun categoriesStateRender() {
		categoriesViewModel.state.collect { categoriesState ->
			when (categoriesState) {
				is CategoriesState.Failure -> {
					Log.d("TAG", "categoriesStateRender: ${categoriesState.e.message}")
					categoriesViewModel.clearState()
				}

				CategoriesState.Loading    -> {}
				is CategoriesState.Success -> {
					val categories = categoriesState.categories
					binding.categoriesContainer.adapter = categoriesAdapter
					categoriesAdapter.submitList(categories)
				}

				CategoriesState.Idle       -> {}
			}
		}
	}

	private fun setupCategoriesContainer() {
		binding.categoriesContainer.apply {
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
			adapter = categoriesAdapter
		}
	}

	private fun searchForProductIntentSend(searchableProduct: String) {
		lifecycleScope.launch {
			productsViewModel.userIntentChannel.send(UserIntents.SearchForProduct(searchableProduct))
		}
	}

	private suspend fun searchForProductsRender() {
		productsViewModel.state.collect { uiState ->
			when (uiState) {
				is ProductsState.Failure -> Log.d(
					"TAG",
					"renderSearchForProducts: ${uiState.e.message}"
				)

				ProductsState.Loading    -> {}
				is ProductsState.Success -> Log.d(
					"TAG",
					"renderSearchForProducts: ${uiState.products}"
				)
			}
		}
	}

	private fun searchForProduct() {
		binding.etSearch.addTextChangedListener(onTextChanged = { chars, _, _, _ ->
			searchForProductIntentSend(chars.toString())
		})
	}

	private fun categoryProductsIntentSend(categoryName: String) {
		lifecycleScope.launch {
			productsViewModel.userIntentChannel.send(UserIntents.CategoryProducts(categoryName))
		}
	}

	private suspend fun categoryProductsIntentRender() {
		productsViewModel.state.collect { uiState ->
			when (uiState) {
				is ProductsState.Failure -> {
					Log.d("TAG", "renderCategoryProductsIntent: ${uiState.e.message}")
				}

				is ProductsState.Loading -> {}
				is ProductsState.Success -> {
					Log.d("TAG", "renderCategoryProductsIntent: ${uiState.products}")
				}
			}
		}
	}

	override fun onClick(categoryName: String) {
		categoryProductsIntentSend(categoryName)
	}
}