<script lang="ts">
  import * as Drawer from "$lib/components/ui/drawer";
  import Button from "./ui/button/button.svelte";
  import { postPedidos } from "$lib/fetchs/fetchPedidos";
  import { Check, ShoppingBasket } from "lucide-svelte";
  import { onMount, createEventDispatcher } from "svelte";
  import { pedidoStore } from "$lib/stores/pedidoStore";
  import { get } from "svelte/store";
  import { ScrollArea } from "$lib/components/ui/scroll-area/index.js";
  import type { ItemPedido } from "$lib/types";
  import { toast } from "svelte-sonner";

  const dispatch = createEventDispatcher();
  let clienteId: number;
  let itemsPedido: ItemPedido[] = [];
  let precoTotalPedido: number;

  const unsubscribe = pedidoStore.subscribe((value) => {
    itemsPedido = value;
  });

  pedidoStore.precoTotalPedido.subscribe(value => {
    precoTotalPedido = value;
  });

  onMount(async () => {
    localStorage.setItem("isAdmin", "false");

    const storedClienteId = localStorage.getItem("clienteId");

    if (storedClienteId) {
      clienteId = parseInt(storedClienteId, 10);
      if (!isNaN(clienteId)) {
        console.log("Cliente ID:", clienteId);
      } else {
        console.error("Cliente ID is not a valid number");
      }
    } else {
      console.error("Cliente não registrado");
    }
  });

  async function postPedidosBtn() {
    if (itemsPedido.length != 0) {
      if (!clienteId) {
        console.error("ClienteId not found.");
        return;
      }
      try {
        const response = await postPedidos(clienteId);

        toast.success("Pedido finalizado com sucesso!", {
          description: "Seu pedido foi enviado para o restaurante.",
        });

        itemsPedido = [];

        dispatch("pedidoCreated", response);
      } catch (error) {
        console.error("Failed to post pedido:", error);
        toast.error("Erro ao finalizar o pedido", {
          description:
            "Ocorreu um erro ao tentar finalizar seu pedido. Tente novamente.",
        });
      }
    } else {
      console.error("0 itens no pedido");
      toast.error("Carrinho vazio", {
        description: "O carrinho está vazio",
      });
    }
  }

  $: unsubscribe();
</script>

<Drawer.Root>
  <Drawer.Trigger>
    <Button variant="buttonBasket" type="submit" class="flex items-center mt-4">
      <ShoppingBasket />
    </Button>
  </Drawer.Trigger>
  <Drawer.Content>
    <div class="mx-auto w-full max-w-sm">
      <Drawer.Header>
        <Drawer.Title>
          Clique em finalizar pedido para realizar seu pedido!
        </Drawer.Title>
        <Drawer.Description>
          Seu pedido vai ser enviado para o restaurante.
        </Drawer.Description>
      </Drawer.Header>
      <div class="p-4">
        <h1 class="text-xl text-center font-bold mb-2">Produtos no carrinho:</h1>
        <div class="flex flex-col ">
          <ScrollArea class="h-[300px] w-[full] rounded-md border-2 p-3">
            {#if itemsPedido.length > 0}
              {#each itemsPedido as item}
                <div class="flex flex-col border rounded-lg p-2 mb-2">
                  <p>
                    <strong>Prato: </strong>{item.produto.nome}
                  </p>
                  <p>Quantidade: {item.quantidade}</p>
                  <h1 class="text-xl font-bold">Preço: {item.precoTotal}</h1>
                </div>
              {/each}
            {:else}
              <p class="text-center">Seu carrinho está vazio.</p>
            {/if}
          </ScrollArea>
        </div>
        <h1 class="text-xl text-center font-bold mt-2">
          Preco total: <span class="text-lime-500">R${precoTotalPedido}</span>
        </h1>
      </div>
      <Drawer.Footer>
        <Button variant="buttonAdd" on:click={postPedidosBtn}
          >Finalizar Pedido!</Button
        >
        <Drawer.Close>Cancelar</Drawer.Close>
      </Drawer.Footer>
    </div>
  </Drawer.Content>
</Drawer.Root>
