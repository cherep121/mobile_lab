package com.example.lunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.AccompanimentMenuScreen
import com.example.lunchtray.ui.CheckoutScreen
import com.example.lunchtray.ui.EntreeMenuScreen
import com.example.lunchtray.ui.OrderViewModel
import com.example.lunchtray.ui.SideDishMenuScreen
import com.example.lunchtray.ui.StartOrderScreen

enum class LunchTrayScreen(@param:StringRes val title: Int) {
    Start(R.string.app_name),
    EntreeMenu(R.string.choose_entree),
    SideDishMenu(R.string.choose_side_dish),
    AccompanimentMenu(R.string.choose_accompaniment),
    Checkout(R.string.order_checkout),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LunchTrayScreen.entries.firstOrNull {
        it.name == backStackEntry?.destination?.route
    } ?: LunchTrayScreen.Start
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            LunchTrayAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = navController::navigateUp,
            )
        },
    ) { innerPadding ->
        val cancelOrder: () -> Unit = {
            viewModel.resetOrder()
            navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
        }

        NavHost(
            navController = navController,
            startDestination = LunchTrayScreen.Start.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(LunchTrayScreen.Start.name) {
                StartOrderScreen(
                    onStartOrderButtonClicked = {
                        navController.navigate(LunchTrayScreen.EntreeMenu.name)
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable(LunchTrayScreen.EntreeMenu.name) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = cancelOrder,
                    onNextButtonClicked = {
                        navController.navigate(LunchTrayScreen.SideDishMenu.name)
                    },
                    onSelectionChanged = viewModel::updateEntree,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .verticalScroll(rememberScrollState()),
                )
            }
            composable(LunchTrayScreen.SideDishMenu.name) {
                SideDishMenuScreen(
                    options = DataSource.sideDishMenuItems,
                    onCancelButtonClicked = cancelOrder,
                    onNextButtonClicked = {
                        navController.navigate(LunchTrayScreen.AccompanimentMenu.name)
                    },
                    onSelectionChanged = viewModel::updateSideDish,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .verticalScroll(rememberScrollState()),
                )
            }
            composable(LunchTrayScreen.AccompanimentMenu.name) {
                AccompanimentMenuScreen(
                    options = DataSource.accompanimentMenuItems,
                    onCancelButtonClicked = cancelOrder,
                    onNextButtonClicked = {
                        navController.navigate(LunchTrayScreen.Checkout.name)
                    },
                    onSelectionChanged = viewModel::updateAccompaniment,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .verticalScroll(rememberScrollState()),
                )
            }
            composable(LunchTrayScreen.Checkout.name) {
                CheckoutScreen(
                    orderUiState = uiState,
                    onNextButtonClicked = cancelOrder,
                    onCancelButtonClicked = cancelOrder,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .verticalScroll(rememberScrollState()),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LunchTrayAppBar(
    currentScreen: LunchTrayScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        navigationIcon = {
            if (canNavigateBack) {
                TextButton(onClick = navigateUp) {
                    Text(stringResource(R.string.back_button))
                }
            }
        },
    )
}
