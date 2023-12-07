package xyz.poolp.bigmac.app.framework.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class McDonaldsRemote(
    val data: WalletAssetsRemoteData,
)
@Serializable
data class WalletAssetsRemoteData(
    val attributes: WalletAssetsRemoteDataAttributes
)
@Serializable
data class WalletAssetsRemoteDataAttributes(
    val commodity: WalletAssetsRemoteDataAttributesCommodity,
    val security: WalletAssetsRemoteDataAttributesSecurity
)
@Serializable
data class WalletAssetsRemoteDataAttributesCommodity(
    val metal: WalletAssetsRemoteDataAttributesCommodityMetal
)
@Serializable
data class WalletAssetsRemoteDataAttributesCommodityMetal(
    val attributes: WalletAssetsRemoteDataAttributesCommodityMetalAttributes
)
@Serializable
data class WalletAssetsRemoteDataAttributesCommodityMetalAttributes(
    val wallets: List<WalletAssetsRemoteDataAttributesCommodityMetalAttributesWallet>
)
@Serializable
data class WalletAssetsRemoteDataAttributesCommodityMetalAttributesWallet(
    val attributes: WalletAssetsRemoteDataAttributesCommodityMetalAttributesWalletAttributes
)
@Serializable
data class WalletAssetsRemoteDataAttributesCommodityMetalAttributesWalletAttributes(
    @SerialName("cryptocoin_symbol")
    val symbol: String,
    val balance: String,
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurity(
    val etc: WalletAssetsRemoteDataAttributesSecurityEtc,
    val stock: WalletAssetsRemoteDataAttributesSecurityStock,
    val etf: WalletAssetsRemoteDataAttributesSecurityEtf
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtc(
    val attributes: WalletAssetsRemoteDataAttributesSecurityEtcAttributes
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityStock(
    val attributes: WalletAssetsRemoteDataAttributesSecurityStockAttributes
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtf(
    val attributes: WalletAssetsRemoteDataAttributesSecurityEtfAttributes
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtcAttributes(
    val wallets: List<WalletAssetsRemoteDataAttributesSecurityEtcAttributesWallet>
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityStockAttributes(
    val wallets: List<WalletAssetsRemoteDataAttributesSecurityStockAttributesWallet>
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtfAttributes(
    val wallets: List<WalletAssetsRemoteDataAttributesSecurityEtfAttributesWallet>
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtcAttributesWallet(
    val attributes: WalletAssetsRemoteDataAttributesSecurityEtcAttributesWalletAttributes
)

@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtfAttributesWallet(
    val attributes: WalletAssetsRemoteDataAttributesSecurityEtfAttributesWalletAttributes
)
@Serializable
data class WalletAssetsRemoteDataAttributesSecurityStockAttributesWallet(
    val attributes: WalletAssetsRemoteDataAttributesSecurityStockAttributesWalletAttributes
)
@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtcAttributesWalletAttributes(
    @SerialName("cryptocoin_symbol")
    val symbol: String,
    val balance: String,
)
@Serializable
data class WalletAssetsRemoteDataAttributesSecurityEtfAttributesWalletAttributes(
    @SerialName("cryptocoin_symbol")
    val symbol: String,
    val balance: String,
)
@Serializable
data class WalletAssetsRemoteDataAttributesSecurityStockAttributesWalletAttributes(
    @SerialName("cryptocoin_symbol")
    val symbol: String,
    val balance: String,
)
