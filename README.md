# 🛒 Sistema de Pedidos

Sistema de pedidos desenvolvido em Java com Programação Orientada a Objetos pura, sem frameworks — contemplando processamento e análise de pedidos com operações de cálculo, filtragem, ordenação e rankings seguindo princípios de código limpo (DRY, KISS, SOLID).

---

## 📦 Funcionalidades

- Cálculo do valor total de um pedido (preço × quantidade)
- Filtragem de itens por valor mínimo
- Ordenação de itens por preço em ordem decrescente
- Adição de pedidos ao carrinho com **validação de estoque**
- **Desconto automático de estoque** ao adicionar pedido no carrinho
- Cálculo do total gasto por cliente usando `Map<Customer, Double>`
- Identificação do cliente que mais gastou com `Optional<Customer>`
- Agrupamento de pedidos por cliente usando `Map<Customer, List<Order>>`

---

## 🏗️ Estrutura do Projeto

```
src/
├── domain/
│   ├── Customer.java    # Cliente com id e nome
│   ├── Product.java     # Produto com preço e estoque
│   ├── Item.java        # Associação produto + quantidade
│   ├── Order.java       # Pedido com lista de itens
│   └── Cart.java        # Carrinho agrupando pedidos por cliente
├── exception/
│   └── StockProductException.java  # Exceção de estoque insuficiente
├── services/            # Camada de serviços (ainda não utilizada)
└── Main.java
```

---

## 🧩 Modelo de Domínio

| Classe      | Descrição |
|-------------|-----------||
| `Customer`  | Representa o cliente com `id` e `name`, usado como chave no `Map` do carrinho |
| `Product`   | Produto com `id`, `name`, `price` e `stock` |
| `Item`      | Associa um `Product` a uma `quantity` específica |
| `Order`     | Pedido contendo uma lista de `Item` e pertencente a um `Customer` |
| `Cart`      | Gerencia pedidos usando `Map<Customer, List<Order>>` |

---

## ⚙️ Regras de Negócio

- Produtos com **estoque insuficiente** não podem ser adicionados ao carrinho — lança `StockProductException`
- O estoque é **descontado automaticamente** ao adicionar o pedido no carrinho via `addToCart()`
- O método `calculateTotalValue()` apenas calcula o total, **sem alterar o estoque**
- Cliente é identificado pelo `id` no `equals/hashCode`, permitindo uso como chave no `Map`
- `customerWhoSpentTheMost()` retorna `Optional.empty()` quando o carrinho está vazio

---

## 🚨 Exceções Customizadas

| Exceção | Quando é lançada |
|---------|------------------|
| `StockProductException` | Quantidade solicitada excede o estoque disponível do produto |

---

## ▶️ Como Executar

1. Clone o repositório
   ```bash
   git clone https://github.com/<seu-usuario>/SistemaDePedidos.git
   ```
2. Compile e execute
   ```bash
   javac src/Main.java
   java -cp src Main
   ```

---

## 🛠️ Tecnologias Utilizadas

- Java (POO pura, sem frameworks)
- Collections (`Map`, `List`, `Optional`)
- Exception handling customizado

---

## 📌 Roadmap / Próximos Passos

### Level 1 — Core ✅
- [x] Calcular total de um pedido
- [x] Filtrar pedidos por valor mínimo
- [x] Ordenar pedidos por valor

### Level 2 — Intermediate ✅
- [x] Agrupar pedidos por cliente
- [x] Total gasto por cliente
- [x] Cliente que mais gastou

### Level 3 — Advanced ⏳
- [ ] Produto mais vendido
- [ ] Top 3 clientes que mais gastaram
- [ ] Média de valor dos pedidos

### Phase 2 — Refactoring
- [ ] Refatoração completa com Java Streams e API funcional
