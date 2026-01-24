import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeService } from '../../shared/employee.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-employee-form',
  templateUrl: './employee-form.component.html'
})
export class EmployeeFormComponent {
  @Output() saved = new EventEmitter<void>();
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private api: EmployeeService,
    private toast: ToastrService
  )  {
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      department: ['', Validators.required],
      salary: [0, Validators.required]
    });
  }


  submit() {
    if (this.form.invalid) return;

    this.api.create(this.form.value as any).subscribe({
      next: () => {
        this.toast.success('Employee created');
        this.form.reset({ salary: 0 });
        this.saved.emit();
      },
      error: () => this.toast.error('Create failed')
    });
  }
}
