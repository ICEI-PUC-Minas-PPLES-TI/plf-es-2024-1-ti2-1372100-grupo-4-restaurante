<script lang="ts">
  import ButtonNav from "$lib/components/navbar/ButtonNav.svelte";
  import { SquareMenu, Receipt, Utensils, Star, X } from "lucide-svelte";
  import type { ComponentType } from "svelte";
  import type { Icon } from "lucide-svelte";

  let itens: {
    label: string;
    href: string;
    icon: ComponentType<Icon>;
  }[] = [
    { label: "Cardapio", href: "/cardapio", icon: SquareMenu },
    { label: "Pedidos", href: "/pedidos", icon: Receipt },
    { label: "Mesas", href: "/mesas", icon: Utensils },
    { label: "Avaliacoes", href: "/avaliacoes", icon: Star },
  ];

  let navmobile: HTMLElement;

  function openNav() {
    navmobile.classList.remove("-translate-x-full");
    navmobile.classList.add("translate-x-0");
  }

  function closeNav() {
    navmobile.classList.remove("translate-x-0");
    navmobile.classList.add("-translate-x-full");
  }
</script>

<button
  on:click={openNav}
  data-drawer-target="logo-sidebar"
  data-drawer-toggle="logo-sidebar"
  aria-controls="logo-sidebar"
  type="button"
  class="ms-3 mt-2 inline-flex items-center rounded-lg p-2 text-sm text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 sm:hidden"
>
  <span class="sr-only">Open sidebar</span>
  <svg
    class="h-6 w-6"
    aria-hidden="true"
    fill="currentColor"
    viewBox="0 0 20 20"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path
      clip-rule="evenodd"
      fill-rule="evenodd"
      d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"
    ></path>
  </svg>
</button>

<aside
  bind:this={navmobile}
  id="logo-sidebar"
  class="fixed left-0 top-0 z-40 h-screen w-64 -translate-x-full transition-transform sm:translate-x-0"
  aria-label="Sidebar"
>
  <div class="relative h-full overflow-y-auto border bg-gray-100 px-3 py-4">
    <div class="flex justify-between items-center mb-5">
      <a href="/" class="flex items-center">
        <img
          class="self-center whitespace-nowrap text-xl font-semibold"
          src="/logoTIAW.png"
          alt="Logo"
        />
      </a>
      <button
        on:click={closeNav}
        class="inline-flex items-center rounded-lg p-2 text-sm text-gray-500 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-gray-200 sm:hidden"
      >
        <X class="h-6 w-6" />
        <span class="sr-only">Close sidebar</span>
      </button>
    </div>
    <ul class="space-y-2 font-medium">
      {#each itens as item}
        <ButtonNav href={item.href} label={item.label} Icon={item.icon} />
      {/each}
    </ul>
  </div>
</aside>
