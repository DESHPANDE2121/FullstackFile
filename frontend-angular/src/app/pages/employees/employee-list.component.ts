import { Component, OnInit } from '@angular/core';
import { Employee, EmployeeService } from '../../shared/employee.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html'
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  editing: Employee | null = null;

  constructor(private api: EmployeeService, private toast: ToastrService) {}

  ngOnInit() {
    this.load();
  }


  getId(e: Employee): string | undefined {
    return e?.id || (e as any)?._id;
  }
  load() {
    this.api.list().subscribe({
      next: (res) => (this.employees = (res || []).slice().sort((a, b) => (a.fullName || '').localeCompare(b.fullName || ''))),
      error: () => this.toast.error('Failed to load employees')
    });
  }

  startEdit(e: Employee) {
    const id = this.getId(e);
    this.editing = { ...e, id };
  }

  cancelEdit() {
    this.editing = null;
  }

  saveEdit() {
    if (!this.editing?.id) {
      this.toast.error('Missing employee id (cannot update)');
      return;
    }

    this.api.update(this.editing.id, this.editing).subscribe({
      next: () => {
        this.toast.success('Employee updated');
        this.editing = null;
        this.load();
      },
      error: () => this.toast.error('Update failed')
    });
  }

  remove(id?: string) {
    if (!id) {
      this.toast.error('Missing employee id (cannot delete)');
      return;
    }

    this.api.delete(id).subscribe({
      next: () => {
        this.toast.success('Employee deleted');
        this.load();
      },
      error: () => this.toast.error('Delete failed')
    });
  }
}
